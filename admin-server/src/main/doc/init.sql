-- ----------------------------
-- Table structure for sys_authentication
-- ----------------------------
DROP TABLE IF EXISTS `sys_authentication`;
CREATE TABLE `sys_authentication` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(255)  NOT NULL COMMENT '创建者',
  `modifier` varchar(255) NOT NULL COMMENT '修改者',
  PRIMARY KEY (`id`),
  KEY `INDEX_ROLE_ID` (`role_id`) USING BTREE,
  KEY `INDEX_MENU_ID` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) NOT NULL COMMENT '菜单名',
  `code` varchar(255)  NOT NULL COMMENT '菜单code',
  `url` varchar(1024) DEFAULT NULL COMMENT '菜单链接',
  `type` tinyint(4) NOT NULL COMMENT '菜单类型',
  `sort_id` tinyint(4) NOT NULL COMMENT '排序序号',
  `parent_id` bigint(20) NOT NULL COMMENT '父菜单id',
  `is_valid` tinyint(4) NOT NULL COMMENT '是否有效',
  `icon` varchar(255) DEFAULT NULL,
  `system_code` varchar(255) NOT NULL COMMENT '系统code',
  `creator` varchar(255)  NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(255)  NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `INDEX_TYPE` (`type`) USING BTREE,
  KEY `INDEX_IS_VALID` (`is_valid`) USING BTREE,
  KEY `INDEX_CODE` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Table structure for sys_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_project`;
CREATE TABLE `sys_project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255)  NOT NULL COMMENT '项目编码',
  `name` varchar(255) NOT NULL COMMENT '项目名称',
  `ip` varchar(255) NOT NULL COMMENT 'ip地址',
  `creator` varchar(255) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `code` varchar(255) NOT NULL COMMENT '角色编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  `remark` varchar(255)  DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  KEY `INDEX_CODE` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_system
-- ----------------------------
DROP TABLE IF EXISTS `sys_system`;
CREATE TABLE `sys_system` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) NOT NULL COMMENT '系统编码',
  `name` varchar(255) NOT NULL COMMENT '系统名称',
  `url` varchar(255)  NOT NULL COMMENT '系统url',
  `creator` varchar(255) NOT NULL COMMENT '新建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100)  NOT NULL COMMENT '密码',
  `salt` varchar(20)  NOT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `state` tinyint(4) NOT NULL COMMENT '状态  0：禁用   1：正常',
  `creator` varchar(50) NOT NULL COMMENT '新建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) NOT NULL COMMENT '用户组code',
  `name` varchar(255) NOT NULL COMMENT '用户组名称',
  `creator` varchar(255) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `INDEX_CODE` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) NOT NULL COMMENT '用户名，冗余字段',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `INDEX_USER_ID` (`user_id`) USING BTREE,
  KEY `INDEX_ROLE_ID` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Table structure for sys_usergroup_project_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_usergroup_project_rel`;
CREATE TABLE `sys_usergroup_project_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `group_id` bigint(20) NOT NULL COMMENT '用户组id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_GROUP_ID` (`group_id`) USING BTREE,
  KEY `INDEX_PROJECT_ID` (`project_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组合项目关联表';

-- ----------------------------
-- Table structure for sys_usergroup_user_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_usergroup_user_rel`;
CREATE TABLE `sys_usergroup_user_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `group_id` bigint(20) NOT NULL COMMENT '用户组id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) NOT NULL COMMENT '用户名，冗余',
  `admin_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '标记是否为管理员 1-：否，2-：是',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_GROUP_ID` (`group_id`) USING BTREE,
  KEY `INDEX_USER_ID` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组和用户关联表';

INSERT INTO `sys_user` VALUES ('1', 'admin', 'RPWcc9s7L+E=', 'bdFpyIo6', '8737368@qq.com', '', '1', 'admin', '2020-02-19 10:48:20', 'admin', '2020-02-19 10:48:20');


INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'super', '2020-02-07 21:10:53', '2020-02-10 11:30:15', 'admin', 'admin', '超级管理员');
INSERT INTO `sys_role` VALUES ('2', '默认角色', 'default', '2020-02-10 11:30:54', '2020-02-10 11:30:54', 'admin', 'admin', '默认角色');


INSERT INTO `sys_user_role` VALUES ('1', '1', 'admin', '1', '2020-02-03 18:03:42', '2020-02-03 18:03:42', 'admin', 'admin');
INSERT INTO `sys_user_role` VALUES ('2', '1', 'admin', '2', '2020-02-05 12:00:54', '2020-02-05 12:00:54', 'admin', 'admin');


INSERT INTO `sys_system` VALUES ('1', 'admin', '后台管理系统', 'http://localhost:9080/admin-server/html', 'admin', '2020-01-07 15:15:36', 'admin', '2020-01-07 15:15:36', '后台管理系统');


INSERT INTO `sys_menu` VALUES ('1', '系统管理', 'admin', null, '1', '3', '0', '1', 'el-icon-document', 'admin', 'admin', '2019-12-16 14:34:27', 'admin', '2020-02-10 17:20:35', '系统管理');
INSERT INTO `sys_menu` VALUES ('2', '用户管理', 'admin.user', null, '1', '1', '1', '1', 'el-icon-location', 'admin', 'admin', '2019-12-16 14:36:02', 'admin', '2020-02-10 17:27:25', null);
INSERT INTO `sys_menu` VALUES ('3', '用户管理', 'admin.user.list', '/user/userManager.html', '1', '1', '2', '1', 'el-icon-bell', 'admin', 'admin', '2019-12-16 14:37:27', 'admin', '2020-02-07 20:29:51', null);
INSERT INTO `sys_menu` VALUES ('4', '新建用户', 'admin.user.create', 'user/create', '2', '2', '3', '1', null, 'admin', 'admin', '2019-12-16 14:38:29', 'admin', '2020-02-07 21:47:41', null);
INSERT INTO `sys_menu` VALUES ('9', '菜单管理', 'admin.menu.list', '/menu/menuManager.html', '1', '2', '16', '1', 'el-icon-bell', 'admin', 'admin', '2019-12-24 10:27:38', 'admin', '2020-02-11 12:00:32', null);
INSERT INTO `sys_menu` VALUES ('16', '系统管理', 'admin.system', '', '1', '3', '1', '1', 'el-icon-message', 'admin', 'admin', '2020-01-17 10:30:34', 'admin', '2020-02-12 21:27:57', '');
INSERT INTO `sys_menu` VALUES ('17', '子系统管理', 'system.list', '/system/systemManager.html', '1', '1', '16', '1', 'el-icon-location', 'admin', 'admin', '2020-01-17 10:31:52', 'admin', '2020-02-11 12:00:23', '');
INSERT INTO `sys_menu` VALUES ('19', '角色管理', 'admin.role.list', '/role/roleManager.html', '1', '2', '2', '1', 'el-icon-message', 'admin', 'admin', '2020-01-19 11:27:51', 'admin', '2020-02-11 11:59:53', '');
INSERT INTO `sys_menu` VALUES ('20', '查询', 'admin.role.search', '', '2', '1', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:13:44', 'admin', '2020-02-07 21:13:44', '');
INSERT INTO `sys_menu` VALUES ('21', '新建', 'admin.role.create', '', '2', '2', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:14:30', 'admin', '2020-02-07 21:14:30', '');
INSERT INTO `sys_menu` VALUES ('22', '删除', 'admin.role.delete', '', '2', '3', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:16:22', 'admin', '2020-02-07 21:16:22', '');
INSERT INTO `sys_menu` VALUES ('23', '权限', 'admin.role.authentication', '', '2', '4', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:17:01', 'admin', '2020-02-07 21:17:01', '');
INSERT INTO `sys_menu` VALUES ('24', '用户列表', 'admin.role.user.list', '', '2', '5', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:19:35', 'admin', '2020-02-07 21:19:35', '');
INSERT INTO `sys_menu` VALUES ('25', '添加用户', 'admin.role.user.create', '', '2', '6', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:20:35', 'admin', '2020-02-07 21:20:35', '');
INSERT INTO `sys_menu` VALUES ('26', '用户列表-删除', 'admin.role.user.delete', '', '2', '7', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:21:44', 'admin', '2020-02-07 21:21:44', '');
INSERT INTO `sys_menu` VALUES ('27', '查询', 'admin.system.search', '', '2', '1', '17', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:40:37', 'admin', '2020-02-07 21:40:37', '');
INSERT INTO `sys_menu` VALUES ('28', '新建', 'admin.system.create', '', '2', '2', '17', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:41:33', 'admin', '2020-02-07 21:41:33', '');
INSERT INTO `sys_menu` VALUES ('29', '修改', 'admin.role.modify', '', '2', '2', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:44:22', 'admin', '2020-02-07 21:44:22', '');
INSERT INTO `sys_menu` VALUES ('30', '修改', 'admin.system.modify', '', '2', '3', '17', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:45:03', 'admin', '2020-02-07 21:45:03', '');
INSERT INTO `sys_menu` VALUES ('31', '删除', 'admin.system.delete', '', '2', '1', '17', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:46:12', 'admin', '2020-02-07 21:46:12', '');
INSERT INTO `sys_menu` VALUES ('32', '查询', 'admin.user.search', '', '2', '1', '3', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:47:31', 'admin', '2020-02-07 21:47:31', '');
INSERT INTO `sys_menu` VALUES ('33', '修改', 'admin.user.modify', '', '2', '3', '3', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:48:16', 'admin', '2020-02-07 21:48:16', '');
INSERT INTO `sys_menu` VALUES ('34', '删除', 'admin.user.delete', '', '2', '4', '3', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:48:50', 'admin', '2020-02-07 21:48:50', '');
INSERT INTO `sys_menu` VALUES ('35', '新建', 'admin.menu.create', '', '2', '1', '9', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:50:41', 'admin', '2020-02-07 21:50:41', '');
INSERT INTO `sys_menu` VALUES ('36', '修改', 'admin.menu.modify', '', '2', '2', '9', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:51:08', 'admin', '2020-02-07 21:51:08', '');
INSERT INTO `sys_menu` VALUES ('37', '删除', 'admin.menu.delete', '', '2', '3', '9', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 21:51:41', 'admin', '2020-02-07 21:51:41', '');
INSERT INTO `sys_menu` VALUES ('38', '重置密码', 'admin.user.resetPassword', '', '2', '5', '3', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 22:00:51', 'admin', '2020-02-07 22:00:51', '');
INSERT INTO `sys_menu` VALUES ('39', '权限-保存', 'admin.role.authentication.create', '', '2', '9', '19', '1', 'el-icon-document', 'admin', 'admin', '2020-02-07 22:15:33', 'admin', '2020-02-07 22:15:33', '');
INSERT INTO `sys_menu` VALUES ('41', '用户组管理', 'admin.userGroup.list', '/usergroup/userGroupManager.html', '1', '2', '2', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 13:06:34', 'admin', '2020-02-11 13:08:08', '');
INSERT INTO `sys_menu` VALUES ('42', '查询', 'admin.userGroup.search', '', '2', '1', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 13:07:21', 'admin', '2020-02-11 13:07:21', '');
INSERT INTO `sys_menu` VALUES ('43', '新建', 'admin.userGroup.create', '', '2', '2', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 13:09:14', 'admin', '2020-02-11 13:09:14', '');
INSERT INTO `sys_menu` VALUES ('44', '编辑', 'admin.userGroup.modify', '', '2', '3', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 13:09:46', 'admin', '2020-02-11 13:09:46', '');
INSERT INTO `sys_menu` VALUES ('45', '删除', 'admin.userGroup.delete', '', '2', '4', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 13:10:19', 'admin', '2020-02-11 13:10:19', '');
INSERT INTO `sys_menu` VALUES ('46', '用户列表', 'admin.userGroup.user.list', '', '2', '5', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 17:01:31', 'admin', '2020-02-11 17:01:31', '');
INSERT INTO `sys_menu` VALUES ('47', '添加用户', 'admin.userGroup.user.create', '', '2', '6', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 17:02:18', 'admin', '2020-02-11 17:02:18', '');
INSERT INTO `sys_menu` VALUES ('48', '用户列表-删除', 'admin.userGroup.user.delete', '', '2', '7', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 17:03:26', 'admin', '2020-02-11 17:03:26', '');
INSERT INTO `sys_menu` VALUES ('49', '用户列表-设为管理员', 'admin.userGroup.user.admin', '', '2', '8', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 21:17:27', 'admin', '2020-02-11 21:17:27', '');
INSERT INTO `sys_menu` VALUES ('50', '用户列表-取消管理员', 'admin.userGroup.user.cancel', '', '2', '9', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-11 21:18:16', 'admin', '2020-02-11 21:18:16', '');
INSERT INTO `sys_menu` VALUES ('51', '项目管理', 'project', '', '1', '2', '1', '1', 'el-icon-document', 'admin', 'admin', '2020-02-12 21:27:51', 'admin', '2020-02-12 21:27:57', '');
INSERT INTO `sys_menu` VALUES ('52', '项目管理', 'admin.project.list', '/project/projectManager.html', '1', '1', '51', '1', 'el-icon-setting', 'admin', 'admin', '2020-02-12 21:29:38', 'admin', '2020-02-12 21:30:44', '');
INSERT INTO `sys_menu` VALUES ('53', '查询', 'admin.project.search', '', '2', '1', '52', '1', 'el-icon-document', 'admin', 'admin', '2020-02-12 21:31:13', 'admin', '2020-02-12 21:31:13', '');
INSERT INTO `sys_menu` VALUES ('54', '新建', 'admin.project.create', '', '2', '2', '52', '1', 'el-icon-document', 'admin', 'admin', '2020-02-12 21:33:52', 'admin', '2020-02-12 21:33:52', '');
INSERT INTO `sys_menu` VALUES ('55', '编辑', 'admin.project.modify', '', '2', '3', '52', '1', 'el-icon-document', 'admin', 'admin', '2020-02-12 21:35:20', 'admin', '2020-02-12 21:35:20', '');
INSERT INTO `sys_menu` VALUES ('56', '删除', 'admin.project.delete', '', '2', '4', '52', '1', 'el-icon-document', 'admin', 'admin', '2020-02-12 21:35:42', 'admin', '2020-02-12 21:35:42', '');
INSERT INTO `sys_menu` VALUES ('57', '项目列表', 'admin.userGroup.project.list', '', '2', '10', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-15 18:00:07', 'admin', '2020-02-15 18:00:07', '');
INSERT INTO `sys_menu` VALUES ('58', '添加项目', 'admin.userGroup.project.create', '', '2', '11', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-15 18:00:55', 'admin', '2020-02-15 18:00:55', '');
INSERT INTO `sys_menu` VALUES ('59', '项目列表-删除', 'admin.userGroup.project.delete', '', '2', '13', '41', '1', 'el-icon-document', 'admin', 'admin', '2020-02-15 18:01:51', 'admin', '2020-02-15 18:01:51', '');


INSERT INTO `sys_authentication` VALUES ('23', '1', '16', '2020-02-07 21:11:24', '2020-02-07 21:11:24', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('24', '1', '17', '2020-02-07 21:11:24', '2020-02-07 21:11:24', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('36', '1', '21', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('38', '1', '22', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('41', '1', '25', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('42', '1', '26', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('43', '1', '27', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('44', '1', '31', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('45', '1', '28', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('46', '1', '30', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('48', '1', '33', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('49', '1', '34', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('50', '1', '35', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('52', '1', '37', '2020-02-07 21:59:21', '2020-02-07 21:59:21', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('91', '1', '51', '2020-02-12 21:36:12', '2020-02-12 21:36:12', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('92', '1', '52', '2020-02-12 21:36:12', '2020-02-12 21:36:12', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('93', '1', '53', '2020-02-12 21:36:12', '2020-02-12 21:36:12', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('94', '1', '54', '2020-02-12 21:36:12', '2020-02-12 21:36:12', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('95', '1', '55', '2020-02-12 21:36:12', '2020-02-12 21:36:12', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('96', '1', '56', '2020-02-12 21:36:12', '2020-02-12 21:36:12', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('97', '1', '39', '2020-02-19 11:03:02', '2020-02-19 11:03:02', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('103', '1', '1', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('104', '1', '2', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('105', '1', '3', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('106', '1', '32', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('107', '1', '4', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('108', '1', '38', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('109', '1', '41', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('110', '1', '42', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('111', '1', '43', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('112', '1', '44', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('113', '1', '45', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('114', '1', '46', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('115', '1', '47', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('116', '1', '48', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('117', '1', '49', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('118', '1', '50', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('119', '1', '57', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('120', '1', '58', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('121', '1', '59', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('122', '1', '19', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('123', '1', '20', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('124', '1', '29', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('125', '1', '23', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('126', '1', '24', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('127', '1', '9', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');
INSERT INTO `sys_authentication` VALUES ('128', '1', '36', '2020-02-19 11:04:25', '2020-02-19 11:04:25', 'admin', 'admin');


INSERT INTO `sys_user_group` VALUES ('1', 'default', '默认用户组', 'admin', '2020-02-11 13:18:38', 'admin', '2020-02-11 13:18:38', '默认用户组');
INSERT INTO `sys_user_group` VALUES ('2', 'super', '超级管理员组', 'admin', '2020-02-11 21:25:51', 'admin', '2020-02-11 21:25:51', '超级管理员组');


INSERT INTO `sys_usergroup_user_rel` VALUES ('1', '1', '1', 'admin', '2', 'admin', '2020-02-11 21:10:53', 'admin', '2020-02-11 21:22:57');
INSERT INTO `sys_usergroup_user_rel` VALUES ('2', '2', '1', 'admin', '2', 'admin', '2020-02-11 21:26:18', 'admin', '2020-02-11 21:26:18');