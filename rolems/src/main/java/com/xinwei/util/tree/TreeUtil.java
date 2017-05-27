package com.xinwei.util.tree;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;



import com.xinwei.util.spring.SpringFactory;


/**
 * tree的工具类，但是这个工具类相比下，比较特殊。
 * 
 * @author shaoyong
 *
 * @param <T>
 */
public class TreeUtil {
	
	
	private static Method Method_getId ;
	private static Method Method_getPId ;
	private static Method Method_getChildren ;
	private static Method Method_setChildren ;
	
	
	static{
		 try {
			Method_getId  = 	 	TreeEntity.class.getMethod("getId", null);
			Method_getPId = 	 	TreeEntity.class.getMethod("getpId", null);
			Method_getChildren = 	TreeEntity.class.getMethod("getChildren", null);
			Method_setChildren = 	TreeEntity.class.getMethod("setChildren", List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 将集合entitys构造成一个树形的集合。
	 * 
	 * ****泛型T 必须继承TreeEntity类****。
	 * 
	 * 其实T也可以不用继承TreeEntity，如果不继承，代码中，每次遍历都会根据参数entitys 来生成新的Method，效率相比较低。
	 * 所以在TreeEntity中 采用了几个固定 的字段定义,也就能通过TreeEntity来进行生成固定的Method。
	 * @param entitys
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> makeTree(List<T> entitys) throws Exception{
		List<T> tops = new ArrayList<T>();
		//获取第一级菜单
		for (T entity : entitys) 
		{
			//当前用户 是顶级部门,则获取第一级组织（parent_id 为空）。为什么不直接获取顶级组织，因为数据库没有存储顶级组织。
			//当前用户 不是顶级， 则获取与用户相同的 部门
			if(null == Method_getPId.invoke(entity, null)){
				Method_setChildren.invoke(entity, new ArrayList<T>(0));
				tops.add(entity);
			}
		}
		// 删除parentId = null;
		boolean isRemove = entitys.removeAll(tops);
		
		//往一级菜单中 添加子菜单
		makeChildren(tops, entitys);
		
		return tops;
	}
	
	
	
	/**
	 * 在srcDatas来源集合中，找出属于parents集合中的子级。
	 * 
	 * ****泛型T 必须继承TreeEntity类****。
	 * 
	 * @param parents
	 * @param srcDatas
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> void makeChildren(List<T> parents,List<T> srcDatas) throws Exception{
		if(CollectionUtils.isEmpty(parents) || CollectionUtils.isEmpty(srcDatas))
			return;
		
		List<T> nextLevelParents = new ArrayList<T>();
		for (T parent : parents) 
		{
			for (T srcData : srcDatas) 
			{
				if (Method_getId.invoke(parent, null).equals(Method_getPId.invoke(srcData, null))) 
				{
					List<T> children = (List<T>) Method_getChildren.invoke(parent, null);
					Method_setChildren.invoke(srcData, new ArrayList<T>(0));
					children.add(srcData);
					nextLevelParents.add(srcData);
				}
			}
		}
		
		srcDatas.removeAll(nextLevelParents);
		makeChildren(nextLevelParents, srcDatas);
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		//KpiOrgService bean = (KpiOrgService) SpringFactory.getBean("kpiOrgServiceImpl");
		//List<KpiOrg> findAll = bean.findAll(StatConstants.BTS);
		
		//LevelEntity level = new KpiOrg();
		//List makeTree = level.makeTree(findAll);
		//System.out.println(findAll);
		
	}
	
	
	
	
	

	
	
	
	
	
	
}
