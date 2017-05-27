/*
Navicat MySQL Data Transfer

Source Server         : 172.18.5.131
Source Server Version : 50525
Source Host           : 172.18.5.131:3306
Source Database       : fiberopticmis

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2017-05-09 13:56:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for applicationinfo
-- ----------------------------
DROP TABLE IF EXISTS `applicationinfo`;
CREATE TABLE `applicationinfo` (
  `form_no` varchar(20) NOT NULL COMMENT '申请单编号',
  `data_id` varchar(64) DEFAULT NULL COMMENT '数据Id',
  `IDNO` varchar(64) DEFAULT NULL COMMENT '用户Id',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '客户名',
  `tel_no` varchar(128) DEFAULT NULL COMMENT '电话号码',
  `installation_address` varchar(1024) DEFAULT NULL COMMENT '安装地址',
  `applicant` bigint(20) DEFAULT NULL COMMENT '申请人Id',
  `apply_date` datetime DEFAULT NULL COMMENT '创建时间',
  `apply_service` varchar(128) DEFAULT NULL COMMENT '申请服务',
  `proc_inst_id` varchar(128) DEFAULT NULL COMMENT '流程实例ID'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `attachment_id` bigint(20) NOT NULL,
  `attachment_name` varchar(255) DEFAULT NULL,
  `original_filename` varchar(255) DEFAULT NULL COMMENT '文件上传原始名字',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `path` varchar(512) DEFAULT NULL,
  `create_person` varchar(20) DEFAULT NULL COMMENT '上传者ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '上传时间',
  `data_id` varchar(64) DEFAULT NULL COMMENT '所属业务数据Id',
  `group_id` varchar(32) DEFAULT NULL COMMENT '所属业务组Id',
  `notes` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`attachment_id`),
  KEY `idx_annex_pId_type` (`type`,`data_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `no` varchar(64) DEFAULT NULL COMMENT '业务编号',
  `type` varchar(64) DEFAULT NULL COMMENT '业务类型',
  `department_id` int(11) DEFAULT NULL COMMENT '所属组织',
  `dataId` varchar(64) DEFAULT NULL COMMENT '数据编号',
  `dataGroupId` varchar(64) DEFAULT NULL COMMENT '数据分组编号',
  `createTime` datetime DEFAULT NULL COMMENT '数据创建时间',
  `createUser` int(11) DEFAULT NULL COMMENT '数据创建操作员',
  `updateTime` datetime DEFAULT NULL COMMENT '最新更新时间',
  `updateUser` int(11) DEFAULT NULL COMMENT '最新操作操作员',
  `data1` text COMMENT '数据1',
  `data2` text COMMENT '数据2',
  `data3` text COMMENT '数据3',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `queryKey` varchar(64) DEFAULT NULL COMMENT '查询关键字',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='通用业务表';


-- ----------------------------
-- Table structure for security_company
-- ----------------------------
DROP TABLE IF EXISTS `security_company`;
CREATE TABLE `security_company` (
  `company_id` bigint(20) NOT NULL,
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `company_property` varchar(255) DEFAULT NULL,
  `certificate_type` varchar(255) DEFAULT NULL,
  `certificate_id` bigint(20) NOT NULL,
  `register_address` varchar(255) DEFAULT NULL,
  `business_address` varchar(255) DEFAULT NULL,
  `link_man` varchar(255) DEFAULT NULL,
  `link_tel` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `create_person` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `approve_person` varchar(255) DEFAULT NULL,
  `approve_time` timestamp NULL DEFAULT NULL,
  `current_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  KEY `idx_commpany_status` (`current_status`) USING BTREE,
  KEY `idx_commpany_name` (`company_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_company
-- ----------------------------
INSERT INTO `security_company` VALUES ('1000000', 'bjxw', 'bjxw', 'bjxw', '111111111111111111', '', '', '11111111111', '11111111111', 'a@a.A', '123', '2017-05-05 09:35:56', null, null, '0');
INSERT INTO `security_company` VALUES ('1000008', '北京信威', '1', '1', '1', '注册地址', '公司地址', '1', '1', null, '1', '2017-02-14 13:28:00', '审核人', '2017-02-14 13:28:00', '1');
INSERT INTO `security_company` VALUES ('1000009', '杭州信威', '1', '1', '1', '注册地址', '公司地址', '1', '1', null, '1', '2017-02-14 13:28:00', '审核人', '2017-02-14 13:28:00', '0');
INSERT INTO `security_company` VALUES ('1000010', '深圳信威', '1', '1', '1', null, null, null, null, null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for security_department
-- ----------------------------
DROP TABLE IF EXISTS `security_department`;
CREATE TABLE `security_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父id',
  `name` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `remark` varchar(128) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of security_department
-- ----------------------------
INSERT INTO `security_department` VALUES ('1', null, '顶级部门', null, null, null);
INSERT INTO `security_department` VALUES ('2', null, null, null, '99', null);
INSERT INTO `security_department` VALUES ('3', null, null, null, '99', null);

-- ----------------------------
-- Table structure for security_function
-- ----------------------------
DROP TABLE IF EXISTS `security_function`;
CREATE TABLE `security_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  `name` varchar(128) NOT NULL COMMENT '国际化key',
  `action` varchar(128) DEFAULT NULL COMMENT 'action',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `sn` varchar(64) DEFAULT NULL COMMENT '按钮权限关键字',
  `remark` varchar(128) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='按钮功能表';

-- ----------------------------
-- Records of security_function
-- ----------------------------
INSERT INTO `security_function` VALUES ('1', '5', 'addUser', '/management/user/create', '新增用户', '2', 'UserManage:add', '');
INSERT INTO `security_function` VALUES ('2', '8', 'editBtn', '/', '修改按钮', '2', 'Dialog:edit', null);
INSERT INTO `security_function` VALUES ('3', '8', 'deleteBtn', '/', '删除按钮', '3', 'Dialog:delete', null);
INSERT INTO `security_function` VALUES ('4', '8', 'queryBtn', '/', '查询按钮', '4', 'Dialog:query', null);
INSERT INTO `security_function` VALUES ('5', '2', 'printBtn', '/', '打印按钮', '5', 'Nav:print', null);
INSERT INTO `security_function` VALUES ('6', '2', 'exportBtn', '/', '导出按钮', '6', 'Nav:export', null);
INSERT INTO `security_function` VALUES ('7', '3', 'updateUserInfo', '/management/user/updateSelf', '修改用户信息', '2', 'UserInfo:update', null);
INSERT INTO `security_function` VALUES ('8', '4', 'updatePassword', '/management/user/updatePassword', '修改密码', '1', 'UserPassword:save', null);
INSERT INTO `security_function` VALUES ('9', '5', 'queryUser', '/management/user/list', '查询用户', '1', 'UserManage:query', null);
INSERT INTO `security_function` VALUES ('10', '5', 'updateUser', '/management/user/update', '修改用户', '3', 'UserManage:update', null);
INSERT INTO `security_function` VALUES ('11', '5', 'disableUser', '/management/user/updateDisabled', '禁用用户', '4', 'UserManage:disable', null);
INSERT INTO `security_function` VALUES ('12', '5', 'deleteUser', '/management/user/delete', '删除用户', '5', 'UserManage:delete', null);
INSERT INTO `security_function` VALUES ('13', '5', 'resetPassword', '/management/user/resetPassword', '重置密码', '6', 'UserManage:resetpassword', null);
INSERT INTO `security_function` VALUES ('14', '6', 'addRole', '/management/role/create', '新增角色', '2', 'Role:add', null);
INSERT INTO `security_function` VALUES ('15', '6', 'queryRole', '/management/role/list', '查询角色', '1', 'Role:query', null);
INSERT INTO `security_function` VALUES ('16', '6', 'updateRole', '/management/role/update', '修改角色', '3', 'Role:update', null);
INSERT INTO `security_function` VALUES ('17', '6', 'assignRolePermission', '/management/role/updateInfo', '配置角色权限', '4', 'Role:assignpermission', null);
INSERT INTO `security_function` VALUES ('18', '6', 'addRoleUser', '/management/role/addUser', '添加用户', '5', 'Role:adduser', null);
INSERT INTO `security_function` VALUES ('19', '6', 'removeRoleUser', '/management/role/removeUser', '移除用户', '6', 'Role:removeuser', null);
INSERT INTO `security_function` VALUES ('20', '6', 'deleteRole', '/management/role/delete', '删除角色', '7', 'Role:delete', null);
INSERT INTO `security_function` VALUES ('26', '3', 'createProject', '/project/create', '创建项目', '5', 'Project:create', null);
INSERT INTO `security_function` VALUES ('22', '17', 'designatedExpert', '/', '指定评审专家', '2', 'Approve:expert', '');
INSERT INTO `security_function` VALUES ('23', '17', 'expertScoring', '/', '评审专家打分', '3', 'Approve:scoring', '');
INSERT INTO `security_function` VALUES ('24', '17', 'decisionCommittee', '/', '决策委员会意见', '4', 'Approve:committee', '');
INSERT INTO `security_function` VALUES ('25', '17', 'divisionManager', '/', '部门经理审批意见', '5', 'Approve:manager', '');
INSERT INTO `security_function` VALUES ('27', '17', 'designatedExpert', '/', '指定评审专家', '2', 'Camtalk:expert', null);
INSERT INTO `security_function` VALUES ('28', '17', 'expertScoring', '/', '评审专家打分', '3', 'Camtalk:scoring', null);
INSERT INTO `security_function` VALUES ('29', '17', 'decisionCommittee', '/', '决策委员会意见', '4', 'Camtalk:committee', null);
INSERT INTO `security_function` VALUES ('30', '17', 'divisionManager', '/', '部门经理审批意见', '5', 'Camtalk:manager', null);
INSERT INTO `security_function` VALUES ('31', '17', 'designatedExpert', '/', '指定评审专家', '2', 'Lottery:expert', null);
INSERT INTO `security_function` VALUES ('32', '17', 'divisionManager', '/', '部门经理审批意见', '5', 'Lottery:manager', null);
INSERT INTO `security_function` VALUES ('33', '17', 'decisionCommittee', '/', '决策委员会意见', '4', 'Lottery:committee', null);
INSERT INTO `security_function` VALUES ('34', '17', 'expertScoring', '/', '评审专家打分', '3', 'Lottery:scoring', null);
INSERT INTO `security_function` VALUES ('35', '17', 'taskExchange', '/', '项目变更', null, 'CooMarts:change', null);

-- ----------------------------
-- Table structure for security_menu
-- ----------------------------
DROP TABLE IF EXISTS `security_menu`;
CREATE TABLE `security_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父id',
  `name` varchar(128) NOT NULL COMMENT '国际化id',
  `action` varchar(128) DEFAULT NULL COMMENT 'action',
  `imgurl` varchar(128) DEFAULT NULL COMMENT '图标地址',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `sn` varchar(64) DEFAULT NULL COMMENT '菜单权限关键字',
  `remark` varchar(128) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of security_menu
-- ----------------------------
INSERT INTO `security_menu` VALUES ('2', null, 'MENU_USER_CENTER', '#', 'fa-user', '用户中心', '3', 'User:main', '');
INSERT INTO `security_menu` VALUES ('3', '2', 'MENU_USER_INFO', '/views/userCenter/userInfo.html', '/', '用户资料', '51', 'UserInfo:main', '');
INSERT INTO `security_menu` VALUES ('4', '2', 'MENU_MODIFY_PWD', '/views/userCenter/passwordModify.html', '/', '修改密码', '52', 'UserPassword:main', '');
INSERT INTO `security_menu` VALUES ('5', '2', 'MENU_USER_MS', '/views/userCenter/userManage.html', '/', '用户管理', '53', 'UserManage:main', '');
INSERT INTO `security_menu` VALUES ('6', '2', 'MENU_ROLE_MS', '/views/userCenter/roleManage.html', '/', '角色管理', '54', 'Role:main', '');
INSERT INTO `security_menu` VALUES ('7', '2', 'MENU_COMPANY_MS', '/views/userCenter/companyManage.html', '/', '单位管理', '55', 'Company:main', null);
INSERT INTO `security_menu` VALUES ('9', '8', 'MENU_APPLY_OPENACCOUNT', '/views/fiber/openAccount.html', '/', '申请光纤开户', '4', 'Manager:openAccount', null);
INSERT INTO `security_menu` VALUES ('1', null, 'MENU_HOME', '/views/home.html', 'fa-home', '首页', '1', 'User:home', '');
INSERT INTO `security_menu` VALUES ('10', '8', 'MENU_APPLY_EXTENSION', '/views/fiber/extension.html', '/', '申请光纤延期', '5', 'Manager:extension', null);
INSERT INTO `security_menu` VALUES ('11', '8', 'MENU_MY_APPLYTASK', '/views/fiber/taskApply.html', '/', '我的申请任务', '6', 'Manager:myApplyTask', null);
INSERT INTO `security_menu` VALUES ('12', '8', 'MENU_MY_REMAINTASK', '/views/fiber/taskRemain.html', '/', '我的代办任务', '7', 'Manager:myRemainTask', null);
INSERT INTO `security_menu` VALUES ('13', '8', 'MENU_MY_FINISHTASK', '/views/fiber/taskFinish.html', '/', '我的完成任务', '8', 'Manager:myFinishTask', null);
INSERT INTO `security_menu` VALUES ('8', null, 'MENU_FIBEROPTIC_MS', '#', 'fa-home', '光纤业务管理', '2', 'Manager:main', null);

-- ----------------------------
-- Table structure for security_role
-- ----------------------------
DROP TABLE IF EXISTS `security_role`;
CREATE TABLE `security_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '角色名',
  `description` varchar(128) DEFAULT NULL COMMENT '描述信息',
  `parent_id` int(11) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of security_role
-- ----------------------------
INSERT INTO `security_role` VALUES ('1', 'Administrator', 'Administrator', null);
INSERT INTO `security_role` VALUES ('30', 'Seller', 'Seller', null);
INSERT INTO `security_role` VALUES ('31', 'Designer', 'Designer', null);
INSERT INTO `security_role` VALUES ('32', 'ContractReviewer', 'Contract reviewer', null);
INSERT INTO `security_role` VALUES ('33', 'Installer', 'Installer', null);
INSERT INTO `security_role` VALUES ('29', 'Visitor', 'Visitor', null);
INSERT INTO `security_role` VALUES ('34', 'ActivationOperator', 'Activation operator', null);
INSERT INTO `security_role` VALUES ('35', 'InfoConfirmer', 'Info confirmer', null);
INSERT INTO `security_role` VALUES ('36', 'FinancialAdmin', 'Financial administrator', null);
INSERT INTO `security_role` VALUES ('37', 'NetworkAdmin', 'NetworkAdmin', null);
INSERT INTO `security_role` VALUES ('38', 'Warehousekeeper', 'Warehouse keeper', null);

-- ----------------------------
-- Table structure for security_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `security_role_permission`;
CREATE TABLE `security_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `resource_id` int(11) NOT NULL COMMENT '资源id',
  `resource_type` int(11) DEFAULT NULL COMMENT '资源类型（0 菜单  1按钮）',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5763 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of security_role_permission
-- ----------------------------
INSERT INTO `security_role_permission` VALUES ('5667', '29', '9', '0');
INSERT INTO `security_role_permission` VALUES ('5670', '29', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5669', '29', '11', '0');
INSERT INTO `security_role_permission` VALUES ('5668', '29', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5701', '32', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5700', '32', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5699', '32', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5698', '32', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5722', '36', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5727', '31', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5726', '31', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5717', '35', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5671', '29', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5549', '1', '20', '1');
INSERT INTO `security_role_permission` VALUES ('5548', '1', '19', '1');
INSERT INTO `security_role_permission` VALUES ('5547', '1', '18', '1');
INSERT INTO `security_role_permission` VALUES ('5546', '1', '17', '1');
INSERT INTO `security_role_permission` VALUES ('5545', '1', '16', '1');
INSERT INTO `security_role_permission` VALUES ('5544', '1', '14', '1');
INSERT INTO `security_role_permission` VALUES ('5543', '1', '15', '1');
INSERT INTO `security_role_permission` VALUES ('5542', '1', '13', '1');
INSERT INTO `security_role_permission` VALUES ('5541', '1', '12', '1');
INSERT INTO `security_role_permission` VALUES ('5540', '1', '11', '1');
INSERT INTO `security_role_permission` VALUES ('5539', '1', '10', '1');
INSERT INTO `security_role_permission` VALUES ('5538', '1', '1', '1');
INSERT INTO `security_role_permission` VALUES ('5537', '1', '9', '1');
INSERT INTO `security_role_permission` VALUES ('5536', '1', '8', '1');
INSERT INTO `security_role_permission` VALUES ('5535', '1', '26', '1');
INSERT INTO `security_role_permission` VALUES ('5534', '1', '7', '1');
INSERT INTO `security_role_permission` VALUES ('5533', '1', '7', '0');
INSERT INTO `security_role_permission` VALUES ('5532', '1', '6', '0');
INSERT INTO `security_role_permission` VALUES ('5531', '1', '5', '0');
INSERT INTO `security_role_permission` VALUES ('5530', '1', '4', '0');
INSERT INTO `security_role_permission` VALUES ('5529', '1', '3', '0');
INSERT INTO `security_role_permission` VALUES ('5528', '1', '2', '0');
INSERT INTO `security_role_permission` VALUES ('5527', '1', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5526', '1', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5525', '1', '11', '0');
INSERT INTO `security_role_permission` VALUES ('5524', '1', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5523', '1', '9', '0');
INSERT INTO `security_role_permission` VALUES ('5522', '1', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5521', '1', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5666', '29', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5665', '29', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5706', '33', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5705', '33', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5704', '33', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5703', '33', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5725', '31', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5724', '31', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5723', '31', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5716', '35', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5715', '35', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5714', '35', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5713', '35', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5762', '30', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5761', '30', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5721', '36', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5720', '36', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5719', '36', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5718', '36', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5760', '30', '11', '0');
INSERT INTO `security_role_permission` VALUES ('5759', '30', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5758', '30', '9', '0');
INSERT INTO `security_role_permission` VALUES ('5757', '30', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5733', '37', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5732', '37', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5731', '37', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5756', '30', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5730', '38', '9', '0');
INSERT INTO `security_role_permission` VALUES ('5729', '38', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5728', '38', '1', '0');
INSERT INTO `security_role_permission` VALUES ('5712', '34', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5707', '33', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5702', '32', '13', '0');
INSERT INTO `security_role_permission` VALUES ('5711', '34', '12', '0');
INSERT INTO `security_role_permission` VALUES ('5710', '34', '10', '0');
INSERT INTO `security_role_permission` VALUES ('5709', '34', '8', '0');
INSERT INTO `security_role_permission` VALUES ('5708', '34', '1', '0');

-- ----------------------------
-- Table structure for security_user
-- ----------------------------
DROP TABLE IF EXISTS `security_user`;
CREATE TABLE `security_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) DEFAULT NULL COMMENT '部门id',
  `company_name` varchar(64) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL COMMENT '登录名',
  `firstname` varchar(64) DEFAULT NULL,
  `lastname` varchar(64) DEFAULT NULL,
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(32) NOT NULL COMMENT '盐值',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '生日',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `cardno` varchar(32) DEFAULT NULL COMMENT '证件号',
  `cardtype` int(11) DEFAULT NULL COMMENT '证件类型',
  `sex` int(11) DEFAULT NULL COMMENT '性别（0：男  1：女）',
  `imgurl` varchar(128) DEFAULT NULL COMMENT '图片路径',
  `status` int(11) NOT NULL COMMENT '状态(0:有效 1：无效)',
  `isreset` int(11) DEFAULT NULL COMMENT '是否是重置状态（0未重置 1已重置）',
  `isDisabled` int(11) DEFAULT NULL COMMENT '是否禁用（0：未禁用  1：禁用）',
  `approval_status` int(11) DEFAULT NULL COMMENT '审核状态（0：未审核，1：审核通过，2：审核不通过）',
  `remark` varchar(128) DEFAULT NULL COMMENT '扩展信息',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of security_user
-- ----------------------------
INSERT INTO `security_user` VALUES ('1', null, null, 'admin', 'super', 'man', '038881275f750fd04fd540bcd75935d0c261fe36', 'a7e226966d8c9f4f', '121212@foxmail.com', '11213', '2016-10-19 10:59:26', '2017-04-26 10:05:25', '北京市海淀区中关村软件园7号楼', '11111111', '0', '0', null, '0', '0', '0', '1', null);
INSERT INTO `security_user` VALUES ('3', null, null, 'projectManager', 'projectManagerA', '', '3caaa394c80bf51c28ddfaf1b8929b0d977522e5', 'b0287eba6eaad6bb', '123456@163.com', '1365925678', '2016-12-27 21:27:10', '2016-12-27 21:19:19', '', '123456789123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('4', null, '北京信威', 'projectManagerA', 'projectManagerA', '', 'ff6a45c0c28276c72cc182aaba9106904284216c', 'd28c6ecfcf2ef86c', '123456@163.com', '13649312345', '2016-12-28 09:12:33', '2017-03-17 13:45:03', '', '123456789123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('5', null, null, 'offercerA', 'offercerAbc', '', '217e7aca1b7a976d527fcfd862adcf2fed99eecc', 'd05bbe4fdff9147b', '123456@163.com', '13649312345', '2016-12-28 09:20:34', '2017-02-24 09:32:04', '', '123456789123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('6', null, null, 'committeeA', 'committeeA', '', '2f36fa7ea82b2d4ece354580d737a6983c5cf409', '42c6941e9ffecf4c', '123456@163.com', '12345678912', '2016-12-28 09:41:35', '2016-12-28 09:33:41', '', '123456789123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('7', null, null, 'departLeaderA', 'departLeaderA', '', 'bc6bf44d3f46cac1d541edfb20a71f4b642b961d', '4c8303f7ffba999b', '123456@163.com', '123456789', '2016-12-28 09:42:48', '2016-12-28 09:34:54', '', '123456789123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('8', null, null, 'offercerB', 'offercerB', '', '442b112d4acfe258ac88d5c4949e86a139013c1f', '2d35006ac33d92df', '123456@163.com', '13649312345', '2017-01-04 15:18:26', '2017-01-04 15:10:02', '', '123456789123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('9', null, null, 'officerB', 'officerB', '', 'ee33b9e55f05f57c00c769ba3222f26dff8f6d15', 'e767af2cb9e0ced7', '123@163.com', '13649274654', '2017-01-23 13:46:25', '2017-01-23 14:19:19', '', '123456789987654321', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('10', null, null, 'officerC', 'officerC', '', '9a8b0e3ff1083d3931f2c233cdd14e78aab7829d', '5b647eba0b714214', '123@163.com', '13649265432', '2017-01-23 14:42:39', '2017-01-23 14:32:53', '', '123456789987654321', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('11', null, null, '11111111111', '111111111111111', '', '8db951406d59060594b09827f6f76fdb5a80e3c0', '5af3d5d7644aceb5', '', '111111111111', '2017-02-07 14:36:35', '2017-02-07 14:26:43', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('12', null, '深圳信威', 'ttt', '111', '', '45a1d2f475a2fa2019d4791ae276f010775b4b92', '9e93c962b66b0f80', '', '111111111', '2017-02-07 15:11:24', '2017-02-07 15:01:33', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('13', null, null, 'AAAAAAA', 'A', '', 'ac5528b6f3cb098d545aabcec6c6bb39c1885fe8', 'fc1d060f9c3ac9a1', '', '1', '2017-02-08 13:50:24', '2017-02-08 13:40:29', '', '', '0', '0', '', '1', null, '0', '2', null);
INSERT INTO `security_user` VALUES ('14', null, null, 'AAAAAAAAA', 'A', '', '99af30a67bce6186d8ab27392fbc8e79cd0eda5c', '5f7d8520a78db771', '', '1', '2017-02-08 13:51:45', '2017-02-08 13:41:51', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('15', null, null, 'aaaaaaaaaaaaaa', 'a', '', '8fc288bf9b5b3a21bc63533f7b68e7d1b226ddee', 'f871293bcb23b1ac', '', '1', '2017-02-08 13:53:01', '2017-02-08 13:43:06', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('16', null, null, 'qqqqqqqq', 'q', '', '59428d106010de50e39593fe12925da7cb273639', '38cc5c26db24ab5a', '', '1', '2017-02-08 14:41:36', '2017-02-08 14:31:42', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('17', null, null, 'hhhhhhhhhh', 'h', '', '68f9b59de077b3e9b6b738b1f9c99127960830a2', '2f891bf5cb599c78', '', '1', '2017-02-08 14:45:04', '2017-02-08 14:35:09', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('18', null, null, 'nnnnnnnnnn', '1', '', 'fc5c4dbae4f70a3dd534df7601d59a4bb3915d27', '577b031227204b4d', '', '1', '2017-02-08 14:54:08', '2017-02-08 14:44:13', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('19', null, null, 'mmmmmmm', '1', '', '573e5e5e2ac10c5ff1a7d2c38907fc6c9814b022', '7a2e066c46d97e10', '', '1', '2017-02-08 14:56:16', '2017-02-08 14:46:21', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('20', null, null, 'kkkkkkkkk', '1', '', '4b6ca522dfc1d9a0916c796dc774e48bdb912b9a', '3735b4322e330fc3', '', '1', '2017-02-08 14:58:14', '2017-02-08 14:48:19', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('21', null, null, 'ooooooooo', '1', '', '89c1f29cea3b1222748e57427ad4086dfab0de69', '26ec10d8aace63e9', '', '1', '2017-02-08 14:58:35', '2017-02-08 14:48:40', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('22', null, null, 'ppp', '1', '', 'e82d067469a407f2c0aec99d5f8d132ba68bd8e5', 'e7100234d2788c77', '', '1', '2017-02-08 14:59:02', '2017-02-08 14:49:07', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('23', null, null, 'eee', 'q', '', '985365148656ce4ab14ab5dd28e4d26e8245126e', 'dd53f67bcbedc5e9', '', '1', '2017-02-08 14:59:44', '2017-02-08 14:49:49', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('24', null, null, 'uuuuuuuuu', 'xjk', '', '515d377c1dcb2048a83524397134d2376ec41fed', '05afb5580e448c08', '', '1', '2017-02-08 15:48:47', '2017-02-15 10:52:51', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('25', null, null, 'ppp1', 'ss', '', '4f72288ed22b002d49f602cb947afc251ac123b6', 'c03456e1815f13f8', '', '1', '2017-02-08 15:51:30', '2017-02-08 15:41:35', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('26', null, null, 'test', 't', '', '507b2eb2f2de56f1856783f9c0d7be32c92665b5', '3543d06c1b399082', '', '1', '2017-02-15 17:10:33', '2017-02-15 17:00:07', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('27', null, null, '111', '1', '', '305b12331d87565daba0712cc08482aa105b194f', '6d80caee296623e9', '', '1', '2017-02-15 17:31:36', '2017-02-15 17:21:10', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('28', null, '深圳信威', '11111', '111', '', 'b53614f562bd71d2b49379eec72455f802f74d7b', '0c331271dc66e8d5', '', '1WWW', '2017-02-15 21:20:26', '2017-02-15 21:09:59', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('29', null, null, 's222', 's', '', '7348ae15a34c38c105d54108756361e3d7ed0bb1', 'ff0dc4c5c5cb63b9', '', '2w', '2017-02-15 21:21:45', '2017-02-15 21:11:18', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('30', null, null, 'test111', 'test111', '', 'e58ff72d644021201162a9c4b8a7e06f25b0aa22', 'f8998bea3ac74c12', '', '111', '2017-02-16 10:38:27', '2017-02-16 10:27:58', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('31', null, null, 'test222', 'test222', '', 'c272eb8455b00a38ff7622ab0f04e4164946dc72', '10069cfceace10a1', '', '123', '2017-02-16 10:38:55', '2017-02-16 10:28:26', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('32', null, 'xinwei1', 'xjkTest', 'xjkTest', 'xjkTest', 'eef67ea1e0f662029726672e255f7027f3439d80', 'b5a2590eb8a76192', '', '', '2017-02-17 17:40:57', '2017-02-17 17:30:34', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('33', null, 'xinwei2', 'xjkTest02', 'xjkTest02', 'xjkTest02', 'da44e79b5368d0e6ffa91a7d7095cd379c90046a', 'f9232bf17c9c8d11', '', '', '2017-02-17 19:49:41', '2017-02-17 19:39:14', '', '', '0', '0', '', '1', null, '1', '2', null);
INSERT INTO `security_user` VALUES ('34', null, '北京信威', 'threeLeader', 'threeLeader', '', 'fc8abd76dea947e9901e097dd63cd8de50067a5d', 'cec200694a76926b', 'threeLeader@163.com', '123777878978979', '2017-02-21 20:59:11', '2017-02-21 20:48:18', '', '545345346546666666', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('35', null, 'xinwei1', 'test2', '11', '', '583659b97845b86ebfdb37ab52630740715bfb8e', '14c59059344d8189', '', '123456', '2017-02-22 09:48:27', '2017-02-22 09:37:32', '', '', '0', '0', '', '1', null, '0', '2', null);
INSERT INTO `security_user` VALUES ('36', null, 'test4', 'xjkTest03', 'xjkTest03', '', '2dc2af207eda3bfd1425a6dd082bb8b14c22e598', 'a968d78acfc3211a', '123@163.com', '123456', '2017-02-24 10:12:03', '2017-02-24 10:01:00', '', '1234566789966', '0', '0', '', '1', null, '0', '2', null);
INSERT INTO `security_user` VALUES ('37', null, 'xiancootalk', 'test05', 'test05', '', '2fc82452ded20bdf18b54545bd7d103e8b60ba0c', '80e8deefbdb8ac64', '123@163.com', '123', '2017-02-24 17:39:06', '2017-02-24 17:28:01', '', '123456', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('38', null, '北京信威', 'admin0099', 'yxg', '', 'f76b6b087ba8dde63305cff0a24ed11366846267', '892cc50162900396', 'yxg@xinwei.com', '13810230319', '2017-02-27 14:32:32', '2017-02-27 14:21:15', '', '123456789101223123', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('39', null, 'xinwei1', 'TESTT', 'TESTT', '', '98ddc51dfb7b5b75a5194e1636a70619cdc4c1b4', 'caba7d26cf849605', '', '15900000000', '2017-03-01 13:52:01', '2017-03-01 13:40:35', '', '', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('40', null, 'xinwei1', 'test001', 'test001', '', '4f9268f0b1b6605e2a47ad7cbae980b93dab0594', 'f82dd20a2f6c16fe', '', '123456456', '2017-03-01 13:54:55', '2017-03-01 13:43:29', '', '', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('41', null, 'xinwei1', 'TEST11', 'TEST11', '', '70edb4b45a85d6149bc02aeec306f866d8499272', '37f9bcc18ee467fb', '', '15900000000', '2017-03-01 14:05:38', '2017-03-01 13:54:12', '', '', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('42', null, 'xinwei1', '1111111111111111', '1111111111', '', 'bd016602150b149e72d388f4853720c71d94352f', 'dd04956cd3fd483a', '', '11111111111', '2017-03-01 14:06:06', '2017-03-01 13:54:40', '', '', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('43', null, 'xinwei1', 'TEST333', 'TEST333', '', '172cdc454647e5dad61a816c86bcacd29c4bdccf', 'ce145085f0d4bf20', '', '123456', '2017-03-01 14:11:31', '2017-03-01 14:00:05', '', '', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('44', null, 'xinwei1', 'testtttt', 'testtttt', '', '78c074bdbe3487e18a32452cca1e92dd6a10e7f8', '16f97902eb0c696f', '', '123456', '2017-03-01 15:57:00', '2017-03-01 15:45:34', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('45', null, 'shenzhenxinwei', 'xjkTest04', 'xjkTest04', '', '6a4b08f6c0b6b1ec9f23a4dda6dc4df63d2a3781', '3bcca3defe67d4cf', '123@163.com', '123', '2017-03-01 17:43:03', '2017-03-01 17:31:36', '', '123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('46', null, 'xinwei1', 'xjkTest05', 'xjkTest05', '', '65ed4dfda6cb811a3f55864e620c280494bb43fe', 'a7b341cfae7f5c48', '123@163.com', '123', '2017-03-01 17:52:29', '2017-03-01 17:41:03', '', '12345678987654321', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('47', null, 'beijingxinwei2', 'xjkTest06', 'xjkTest06', '', 'd720f96425f3275d713608acec283f9a7150b76d', '203e7cda95a4d898', '123@163.com', '123', '2017-03-01 18:03:16', '2017-03-01 17:51:50', '', '123456', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('48', null, 'beijingxinwei3', 'xjkTest07', 'xjkTest07', '', '944550ac6c85268eb817fe43675cf3c76433c800', '9897044765f8d45c', '123@163.com', '123', '2017-03-02 09:08:26', '2017-03-02 08:57:51', '', '123456789', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('49', null, 'beijingxinwei5', 'xjkTest08', 'xjkTest08', '', '6954676228dcfe4d2b66e97e8455c80fa4e80f08', '95eca4ed18bb3b46', '123@163.com', '123', '2017-03-02 09:47:26', '2017-03-02 09:37:02', '', '123456', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('50', null, '重庆信威', 'threeleader2', 'threeleader2', '', '880ce9b99a487295248cfade0d2cd2220f3a9aef', 'fc67c485fabcfa5f', '', '123456', '2017-03-02 21:05:38', '2017-03-02 20:55:07', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('51', null, '测试李国强', 'lgq', 'lgq', '', '7a811a0795028da598a7b690ee213f7a31fd1a99', 'a4b336475379f34d', 'adfadsf@163.com', '113123132', '2017-03-08 13:17:41', '2017-03-08 13:06:46', '', '', '0', '0', '', '1', null, '0', '0', null);
INSERT INTO `security_user` VALUES ('52', null, 'ceshi', 'lgq1', 'lgq1', '', 'f15fe22c70ce4beeee64ea9c065025e8255809ac', '7e9c31775c00b413', '', '1123123', '2017-03-08 13:19:48', '2017-03-08 13:08:52', '', '', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('53', null, '北京信威', 'saleA', 'saleA', '', '974e2abf24ea0d49d2d67c2692dc6b9391308f55', '3e7c6ee1bd63b0fe', 'sale@163.com', '1213456', '2017-03-21 10:28:05', '2017-03-22 13:23:55', '', '43016546789686', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('54', null, '北京信威', 'fiananceA', 'fiananceA', '', 'cb27c643e1b406bd66c9b17b5204b1d18bcef232', '9579fe9cad7ad706', 'fiananceA@163.com', '1222222222222222222', '2017-03-21 10:31:12', '2017-03-21 10:19:21', '', '1111111333332453543', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('55', null, '北京信威', 'netWorkAdminA', 'netWorkAdminA', '', '0b9f9cdb912c11fff496fd6d9570b1748e7502ba', 'c776ef7c0d47df1e', 'netWorkAdmin@163.com', '13333333', '2017-03-21 10:32:00', '2017-03-22 13:24:10', '', '133333333', '0', '0', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('56', null, '北京信威', 'sellerA', 'sellerA', '', '11996dac000b3cb1d66a4dcbf7833e5fd65c3d69', '21c22941e13991f6', '123@163.com', '13666688888', '2017-04-14 15:54:40', '2017-04-14 15:42:20', '', '100000000000000', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('57', null, '北京信威', 'SellerB', 'SellerB', '', '3302b8bd377acd2330fa7f123c8e2a1ccb114401', 'cdc31308bf8e1342', '', '1', '2017-04-14 16:56:51', '2017-04-14 16:44:30', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('58', null, '北京信威', 'DesignerA', 'DesignerA', '', 'fe1618bcf5e4e2575de9f94502f2455b731754e3', '03b199f7018bf3e3', '', '1232', '2017-04-20 16:14:57', '2017-04-20 16:02:11', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('59', null, '北京信威', 'ContractReviewerA', 'ContractReviewerA', '', 'bfe31f4a32688e78dbf3f7854f95fb921449beb7', '441c7e9f7fe26320', '', '123', '2017-04-20 16:16:33', '2017-04-20 16:03:46', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('60', null, '北京信威', 'InstallerA', 'InstallerA', '', '1338dee57b0ccf7dc5f610f7167b386bf50f9980', 'f414e6f9c2869553', '', '123', '2017-04-20 16:17:32', '2017-04-20 16:04:45', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('61', null, '北京信威', 'VisitorA', 'VisitorA', '', 'e0b1d4d39a185991058d5ea5334743e31ddc3815', 'e8a435d5b4f4e5de', '', '123', '2017-04-20 16:18:09', '2017-04-20 16:05:23', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('62', null, '北京信威', 'ActivationOperatorA', 'ActivationOperatorA', '', '67c4f38dd31d575f1812140b809977ca2684d932', 'c12ff91ff062b989', '', '123', '2017-04-20 16:19:07', '2017-04-20 16:06:20', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('63', null, '北京信威', 'InfoConfirmerA', 'InfoConfirmerA', '', 'b0392a420bec0cabd7f1c3625694c33f91f99dab', 'f53d495d9ca2e9e9', '', '123', '2017-04-20 16:19:54', '2017-04-20 16:07:08', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('64', null, '北京信威', 'FinancialAdminA', 'FinancialAdminA', '', '34c62b523f645c66d2f953343c5ba1581a033eaf', '1698b933d4d07ae3', '', '123', '2017-04-20 16:20:52', '2017-04-20 16:08:05', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('65', null, '北京信威', 'NetworkAdminA', 'NetworkAdminA', '', 'e90ae3decb51203a8c97ad9d3ed655843b3c2f0b', 'd5c51e8f95a4eea2', '', '123', '2017-04-20 16:21:39', '2017-04-20 16:08:53', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('66', null, '北京信威', 'WarehouseKeeperA', 'WarehouseKeeperA', '', '2e75aca420b5b4c704d2d1046bec69ba1874a255', '49c075d63ebda5d2', '', '123', '2017-04-20 16:22:19', '2017-04-20 16:09:32', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('67', null, '北京信威', 'networkAdminB', 'networkAdminB', '', 'e611f543f90786bc829f4599c31e8d0a5275f342', 'd9370c1477992fec', '', '123', '2017-04-25 15:26:01', '2017-04-25 15:12:53', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('68', null, '北京信威', '1212', 'f', '', '5960ae808940737c3566def3075fabe72c3476f4', '1546491b6d3fb449', '44@h.h', '44444', '2017-04-26 02:13:20', '2017-04-26 10:03:32', '', 'fgdhdfh', '0', '1', '', '1', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('69', null, '北京信威', 'DesignerB', 'DesignerB', '', '82f97bbd52038c066372425422f16382fd7d5a2d', 'ef24fc102ae86f0a', 'dd@l.l', '123456', '2017-04-27 08:28:32', '2017-04-27 16:23:08', '', '3333333333', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('70', null, '北京信威', 'ContractReviewerB', 'ContractReviewerB', '', 'b08c47a22f2f1c1cca22fb5660fde1af35347471', '384d9f1f894053f2', '', '123', '2017-05-02 02:51:38', '2017-05-02 10:38:26', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('71', null, '北京信威', 'InstallerB', 'InstallerB', '', 'eb13c5b8f02ea091f39d823e355013261616f16d', '018a51ec94a249c1', '', '123', '2017-05-03 02:03:00', '2017-05-03 09:49:44', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('72', null, '北京信威', 'ActivationOperatorB', 'ActivationOperatorB', '', '2d7a2b0bfcb4cceeabec6b1af166d6a858de77c1', '22288882f744b7da', '', '123', '2017-05-03 02:04:13', '2017-05-03 09:50:58', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('73', null, '北京信威', 'InfoConfirmerB', 'InfoConfirmerB', '', 'c8d2f58238632b22fb32b7fc961cd25ba87675e8', '13749f827a385abf', '', '123', '2017-05-03 02:04:42', '2017-05-03 09:51:26', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('74', null, '北京信威', 'FinancialAdminB', 'FinancialAdminB', '', 'bc379f4c1b1329ac2bed3a375e339c7d1c82ec41', 'd27c7736d7ef9649', '', '123', '2017-05-03 02:05:10', '2017-05-03 09:51:54', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('75', null, '北京信威', 'rqweqwe', 'erqwerqw', '', 'd49dd1b26815602c3caa52fa0c052d8069ab0b63', '6770b7159dfc5403', '', '1212121212', '2017-05-03 10:35:31', '2017-05-03 18:22:14', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('76', null, '北京信威', 'DesignerC', 'DesignerC', '', 'bada7bf2bf87fb2131a22ea5fbff0b3954902cf4', '3def1cd20732a021', '', '123', '2017-05-03 10:49:56', '2017-05-03 18:36:39', '', '', '0', '0', '', '0', null, '0', '1', null);
INSERT INTO `security_user` VALUES ('77', null, '北京信威', 'wja', 'wja', '', '15cec88bc5dfad5c9b6707b882a26f87f8d07ba8', 'fd947957ca73b108', 'a@a.a', '111', '2017-05-05 09:38:48', '2017-05-05 17:30:26', '', '1111111111111', '0', '1', '', '0', null, '0', '0', null);

-- ----------------------------
-- Table structure for security_user_role
-- ----------------------------
DROP TABLE IF EXISTS `security_user_role`;
CREATE TABLE `security_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of security_user_role
-- ----------------------------
INSERT INTO `security_user_role` VALUES ('1', '1', '1');
INSERT INTO `security_user_role` VALUES ('86', '57', '30');
INSERT INTO `security_user_role` VALUES ('84', '56', '30');
INSERT INTO `security_user_role` VALUES ('88', '59', '32');
INSERT INTO `security_user_role` VALUES ('87', '58', '31');
INSERT INTO `security_user_role` VALUES ('90', '61', '29');
INSERT INTO `security_user_role` VALUES ('89', '60', '33');
INSERT INTO `security_user_role` VALUES ('92', '63', '35');
INSERT INTO `security_user_role` VALUES ('91', '62', '34');
INSERT INTO `security_user_role` VALUES ('93', '64', '36');
INSERT INTO `security_user_role` VALUES ('94', '65', '37');
INSERT INTO `security_user_role` VALUES ('95', '66', '38');
INSERT INTO `security_user_role` VALUES ('96', '67', '37');
INSERT INTO `security_user_role` VALUES ('98', '69', '31');
INSERT INTO `security_user_role` VALUES ('99', '70', '32');
INSERT INTO `security_user_role` VALUES ('100', '71', '33');
INSERT INTO `security_user_role` VALUES ('101', '72', '34');
INSERT INTO `security_user_role` VALUES ('102', '73', '35');
INSERT INTO `security_user_role` VALUES ('103', '74', '36');
INSERT INTO `security_user_role` VALUES ('104', '75', '37');
INSERT INTO `security_user_role` VALUES ('105', '76', '31');
INSERT INTO `security_user_role` VALUES ('106', '77', '29');

-- ----------------------------
-- Table structure for xw_sequence
-- ----------------------------
DROP TABLE IF EXISTS `xw_sequence`;
CREATE TABLE `xw_sequence` (
  `SEQ_NAME` varchar(64) NOT NULL,
  `INIT_VALUE` decimal(16,0) NOT NULL,
  `MAX_VALUE` decimal(16,0) NOT NULL,
  `CURRENT_VALUE` decimal(16,0) NOT NULL,
  `SEQ_STEP` decimal(8,0) NOT NULL,
  `TABLE_CIRCLE_SIZE` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xw_sequence
-- ----------------------------
INSERT INTO `xw_sequence` VALUES ('attachment_seq', '1000000', '9999999', '1000377', '1', '10');
INSERT INTO `xw_sequence` VALUES ('company_seq', '1000011', '9999999', '1000001', '1', '10');

-- ----------------------------
-- Procedure structure for pro_getSequenceByName
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_getSequenceByName`;
DELIMITER ;;
CREATE PROCEDURE `pro_getSequenceByName`( IN sn varchar(64),IN amt numeric(16,0),OUT seqName varchar(64),OUT initValue numeric(16,0),OUT max_Value numeric

(16,0),OUT currentValue numeric(16,0),OUT seqStep numeric(16,0), OUT tableCircleSize numeric(16,0))
BEGIN
	#Routine body goes here...
 DECLARE t_error INTEGER DEFAULT 0;  
 DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1; 

  SELECT * INTO seqName,initValue,max_Value,currentValue,seqStep, tableCircleSize FROM xw_sequence WHERE seq_name = sn FOR Update;
  UPDATE xw_sequence SET current_value = current_value + seqStep*amt WHERE seq_name = sn;

 IF t_error = 1 THEN  
     ROLLBACK;  
 ELSE  
 COMMIT;  
 END IF;  
END
;;
DELIMITER ;
