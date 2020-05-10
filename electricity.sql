/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 47.113.118.101:3306
 Source Schema         : electricity

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 10/05/2020 20:09:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin`  (
  `pk_admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `admin_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员名称',
  `admin_password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员密码',
  `admin_status` int(1) NOT NULL COMMENT '管理员状态 0：停用 1：启用',
  `admin_level` int(1) NOT NULL COMMENT '管理员等级 0：超级管理员 1：普通管理员',
  `admin_real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员真实姓名',
  `admin_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员电话',
  PRIMARY KEY (`pk_admin_id`, `admin_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES (1, 'admin', 'admin', 1, 0, '超级管理员', '13512345670');
INSERT INTO `tb_admin` VALUES (3, 'admin01', 'admin01', 1, 1, '管理员01', '13512345671');
INSERT INTO `tb_admin` VALUES (4, 'admin02', 'admin02', 1, 1, '管理员02', '13512345672');

-- ----------------------------
-- Table structure for tb_consume
-- ----------------------------
DROP TABLE IF EXISTS `tb_consume`;
CREATE TABLE `tb_consume`  (
  `pk_consume_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消费记录ID',
  `consume_date` date NOT NULL COMMENT '消费日期',
  `consume_kwh` double(10, 2) NOT NULL COMMENT '用电量（度）',
  `consume_amount` double(10, 2) NOT NULL COMMENT '消费金额',
  `consume_balance` double(10, 2) NOT NULL COMMENT '电费余额',
  `dormitory_id` int(11) NOT NULL COMMENT '宿舍ID，外键',
  PRIMARY KEY (`pk_consume_id`) USING BTREE,
  INDEX `dormitory_id`(`dormitory_id`) USING BTREE,
  CONSTRAINT `tb_consume_ibfk_1` FOREIGN KEY (`dormitory_id`) REFERENCES `tb_dormitory` (`pk_dormitory_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1391 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_consume
-- ----------------------------
INSERT INTO `tb_consume` VALUES (528, '2020-01-01', 6.68, 3.49, 96.51, 1);
INSERT INTO `tb_consume` VALUES (529, '2020-01-02', 1.34, 0.70, 95.81, 1);
INSERT INTO `tb_consume` VALUES (530, '2020-01-03', 2.42, 1.26, 94.55, 1);
INSERT INTO `tb_consume` VALUES (531, '2020-01-04', 9.75, 5.09, 89.46, 1);
INSERT INTO `tb_consume` VALUES (532, '2020-01-05', 4.46, 2.33, 87.13, 1);
INSERT INTO `tb_consume` VALUES (533, '2020-01-06', 7.33, 3.83, 83.30, 1);
INSERT INTO `tb_consume` VALUES (534, '2020-01-07', 0.44, 0.23, 83.07, 1);
INSERT INTO `tb_consume` VALUES (535, '2020-01-08', 4.72, 2.47, 80.60, 1);
INSERT INTO `tb_consume` VALUES (536, '2020-01-09', 8.50, 4.44, 76.16, 1);
INSERT INTO `tb_consume` VALUES (537, '2020-01-10', 6.46, 3.37, 72.79, 1);
INSERT INTO `tb_consume` VALUES (538, '2020-01-11', 0.99, 0.52, 72.27, 1);
INSERT INTO `tb_consume` VALUES (539, '2020-01-12', 0.63, 0.33, 71.94, 1);
INSERT INTO `tb_consume` VALUES (540, '2020-01-13', 9.49, 4.96, 66.98, 1);
INSERT INTO `tb_consume` VALUES (541, '2020-01-14', 9.58, 5.00, 61.98, 1);
INSERT INTO `tb_consume` VALUES (542, '2020-01-15', 6.01, 3.14, 58.84, 1);
INSERT INTO `tb_consume` VALUES (543, '2020-01-16', 0.03, 0.02, 58.82, 1);
INSERT INTO `tb_consume` VALUES (544, '2020-01-17', 3.40, 1.78, 57.04, 1);
INSERT INTO `tb_consume` VALUES (545, '2020-01-18', 6.32, 3.30, 53.74, 1);
INSERT INTO `tb_consume` VALUES (546, '2020-01-19', 4.39, 2.29, 51.45, 1);
INSERT INTO `tb_consume` VALUES (547, '2020-01-20', 1.74, 0.91, 50.54, 1);
INSERT INTO `tb_consume` VALUES (548, '2020-01-21', 8.30, 4.34, 46.20, 1);
INSERT INTO `tb_consume` VALUES (549, '2020-01-22', 1.20, 0.63, 45.57, 1);
INSERT INTO `tb_consume` VALUES (550, '2020-01-23', 1.49, 0.78, 44.79, 1);
INSERT INTO `tb_consume` VALUES (551, '2020-01-24', 2.55, 1.33, 43.46, 1);
INSERT INTO `tb_consume` VALUES (552, '2020-01-25', 9.96, 5.20, 38.26, 1);
INSERT INTO `tb_consume` VALUES (553, '2020-01-26', 0.36, 0.19, 38.07, 1);
INSERT INTO `tb_consume` VALUES (554, '2020-01-27', 1.98, 1.03, 37.04, 1);
INSERT INTO `tb_consume` VALUES (555, '2020-01-28', 3.79, 1.98, 35.06, 1);
INSERT INTO `tb_consume` VALUES (556, '2020-01-29', 6.17, 3.22, 31.84, 1);
INSERT INTO `tb_consume` VALUES (557, '2020-01-30', 1.51, 0.79, 31.05, 1);
INSERT INTO `tb_consume` VALUES (558, '2020-01-31', 5.64, 2.95, 28.10, 1);
INSERT INTO `tb_consume` VALUES (559, '2020-02-01', 2.83, 1.48, 26.62, 1);
INSERT INTO `tb_consume` VALUES (560, '2020-02-02', 7.80, 4.07, 22.55, 1);
INSERT INTO `tb_consume` VALUES (561, '2020-02-03', 3.35, 1.75, 20.80, 1);
INSERT INTO `tb_consume` VALUES (562, '2020-02-04', 1.67, 0.87, 19.93, 1);
INSERT INTO `tb_consume` VALUES (563, '2020-02-05', 4.23, 2.21, 17.72, 1);
INSERT INTO `tb_consume` VALUES (564, '2020-02-06', 7.18, 3.75, 13.97, 1);
INSERT INTO `tb_consume` VALUES (565, '2020-02-07', 8.10, 4.23, 9.74, 1);
INSERT INTO `tb_consume` VALUES (566, '2020-02-08', 3.28, 1.71, 8.03, 1);
INSERT INTO `tb_consume` VALUES (567, '2020-02-09', 4.71, 2.46, 5.57, 1);
INSERT INTO `tb_consume` VALUES (568, '2020-02-10', 8.34, 4.36, 1.21, 1);
INSERT INTO `tb_consume` VALUES (569, '2020-02-11', 1.47, 0.77, 0.44, 1);
INSERT INTO `tb_consume` VALUES (570, '2020-02-12', 2.91, 1.52, -1.08, 1);
INSERT INTO `tb_consume` VALUES (571, '2020-02-13', 0.02, 0.01, 98.91, 1);
INSERT INTO `tb_consume` VALUES (572, '2020-02-14', 6.84, 3.57, 95.34, 1);
INSERT INTO `tb_consume` VALUES (573, '2020-02-15', 8.94, 4.67, 90.67, 1);
INSERT INTO `tb_consume` VALUES (574, '2020-02-16', 9.75, 5.09, 85.58, 1);
INSERT INTO `tb_consume` VALUES (575, '2020-02-17', 9.20, 4.81, 80.77, 1);
INSERT INTO `tb_consume` VALUES (576, '2020-02-18', 0.24, 0.13, 80.64, 1);
INSERT INTO `tb_consume` VALUES (577, '2020-02-19', 6.90, 3.60, 77.04, 1);
INSERT INTO `tb_consume` VALUES (578, '2020-02-20', 6.15, 3.21, 73.83, 1);
INSERT INTO `tb_consume` VALUES (579, '2020-02-21', 4.10, 2.14, 71.69, 1);
INSERT INTO `tb_consume` VALUES (580, '2020-02-22', 3.92, 2.05, 69.64, 1);
INSERT INTO `tb_consume` VALUES (581, '2020-02-23', 8.11, 4.24, 65.40, 1);
INSERT INTO `tb_consume` VALUES (582, '2020-02-24', 9.00, 4.70, 60.70, 1);
INSERT INTO `tb_consume` VALUES (583, '2020-02-25', 4.14, 2.16, 58.54, 1);
INSERT INTO `tb_consume` VALUES (584, '2020-02-26', 6.45, 3.37, 55.17, 1);
INSERT INTO `tb_consume` VALUES (585, '2020-02-27', 0.73, 0.38, 54.79, 1);
INSERT INTO `tb_consume` VALUES (586, '2020-02-28', 5.44, 2.84, 51.95, 1);
INSERT INTO `tb_consume` VALUES (587, '2020-02-29', 9.90, 5.17, 46.78, 1);
INSERT INTO `tb_consume` VALUES (588, '2020-03-01', 8.82, 4.61, 42.17, 1);
INSERT INTO `tb_consume` VALUES (589, '2020-03-02', 6.51, 3.40, 38.77, 1);
INSERT INTO `tb_consume` VALUES (590, '2020-03-03', 6.19, 3.23, 35.54, 1);
INSERT INTO `tb_consume` VALUES (591, '2020-03-04', 2.16, 1.13, 34.41, 1);
INSERT INTO `tb_consume` VALUES (592, '2020-03-05', 1.92, 1.00, 33.41, 1);
INSERT INTO `tb_consume` VALUES (593, '2020-03-06', 5.59, 2.92, 30.49, 1);
INSERT INTO `tb_consume` VALUES (594, '2020-03-07', 5.97, 3.12, 27.37, 1);
INSERT INTO `tb_consume` VALUES (595, '2020-03-08', 6.65, 3.47, 23.90, 1);
INSERT INTO `tb_consume` VALUES (596, '2020-03-09', 6.57, 3.43, 20.47, 1);
INSERT INTO `tb_consume` VALUES (597, '2020-03-10', 7.00, 3.66, 16.81, 1);
INSERT INTO `tb_consume` VALUES (598, '2020-03-11', 7.65, 4.00, 12.81, 1);
INSERT INTO `tb_consume` VALUES (599, '2020-03-12', 8.03, 4.19, 8.62, 1);
INSERT INTO `tb_consume` VALUES (600, '2020-03-13', 1.65, 0.86, 7.76, 1);
INSERT INTO `tb_consume` VALUES (601, '2020-03-14', 5.74, 3.00, 4.76, 1);
INSERT INTO `tb_consume` VALUES (602, '2020-03-15', 8.08, 4.22, 0.54, 1);
INSERT INTO `tb_consume` VALUES (603, '2020-03-16', 2.04, 1.07, -0.53, 1);
INSERT INTO `tb_consume` VALUES (604, '2020-01-01', 9.88, 5.16, 294.84, 2);
INSERT INTO `tb_consume` VALUES (605, '2020-01-02', 2.25, 1.18, 293.66, 2);
INSERT INTO `tb_consume` VALUES (606, '2020-01-03', 2.29, 1.20, 292.46, 2);
INSERT INTO `tb_consume` VALUES (607, '2020-01-04', 9.10, 4.75, 287.71, 2);
INSERT INTO `tb_consume` VALUES (608, '2020-01-05', 9.56, 4.99, 282.72, 2);
INSERT INTO `tb_consume` VALUES (609, '2020-01-06', 9.16, 4.79, 277.93, 2);
INSERT INTO `tb_consume` VALUES (610, '2020-01-07', 2.46, 1.29, 276.64, 2);
INSERT INTO `tb_consume` VALUES (611, '2020-01-08', 0.64, 0.33, 276.31, 2);
INSERT INTO `tb_consume` VALUES (612, '2020-01-09', 7.26, 3.79, 272.52, 2);
INSERT INTO `tb_consume` VALUES (613, '2020-01-10', 1.46, 0.76, 271.76, 2);
INSERT INTO `tb_consume` VALUES (614, '2020-01-11', 0.28, 0.15, 271.61, 2);
INSERT INTO `tb_consume` VALUES (615, '2020-01-12', 3.29, 1.72, 269.89, 2);
INSERT INTO `tb_consume` VALUES (616, '2020-01-13', 4.88, 2.55, 267.34, 2);
INSERT INTO `tb_consume` VALUES (617, '2020-01-14', 3.60, 1.88, 265.46, 2);
INSERT INTO `tb_consume` VALUES (618, '2020-01-15', 0.83, 0.43, 265.03, 2);
INSERT INTO `tb_consume` VALUES (619, '2020-01-16', 3.01, 1.57, 263.46, 2);
INSERT INTO `tb_consume` VALUES (620, '2020-01-17', 5.95, 3.11, 260.35, 2);
INSERT INTO `tb_consume` VALUES (621, '2020-01-18', 6.44, 3.36, 256.99, 2);
INSERT INTO `tb_consume` VALUES (622, '2020-01-19', 3.44, 1.80, 255.19, 2);
INSERT INTO `tb_consume` VALUES (623, '2020-01-20', 4.40, 2.30, 252.89, 2);
INSERT INTO `tb_consume` VALUES (624, '2020-01-21', 5.24, 2.74, 250.15, 2);
INSERT INTO `tb_consume` VALUES (625, '2020-01-22', 2.04, 1.07, 249.08, 2);
INSERT INTO `tb_consume` VALUES (626, '2020-01-23', 4.04, 2.11, 246.97, 2);
INSERT INTO `tb_consume` VALUES (627, '2020-01-24', 3.64, 1.90, 245.07, 2);
INSERT INTO `tb_consume` VALUES (628, '2020-01-25', 7.24, 3.78, 241.29, 2);
INSERT INTO `tb_consume` VALUES (629, '2020-01-26', 1.68, 0.88, 240.41, 2);
INSERT INTO `tb_consume` VALUES (630, '2020-01-27', 3.95, 2.06, 238.35, 2);
INSERT INTO `tb_consume` VALUES (631, '2020-01-28', 8.21, 4.29, 234.06, 2);
INSERT INTO `tb_consume` VALUES (632, '2020-01-29', 8.05, 4.21, 229.85, 2);
INSERT INTO `tb_consume` VALUES (633, '2020-01-30', 9.47, 4.95, 224.90, 2);
INSERT INTO `tb_consume` VALUES (634, '2020-01-31', 1.94, 1.01, 223.89, 2);
INSERT INTO `tb_consume` VALUES (635, '2020-02-01', 2.18, 1.14, 222.75, 2);
INSERT INTO `tb_consume` VALUES (636, '2020-02-02', 0.80, 0.42, 222.33, 2);
INSERT INTO `tb_consume` VALUES (637, '2020-02-03', 3.47, 1.81, 220.52, 2);
INSERT INTO `tb_consume` VALUES (638, '2020-02-04', 4.52, 2.36, 218.16, 2);
INSERT INTO `tb_consume` VALUES (639, '2020-02-05', 3.67, 1.92, 216.24, 2);
INSERT INTO `tb_consume` VALUES (640, '2020-02-06', 3.27, 1.71, 214.53, 2);
INSERT INTO `tb_consume` VALUES (641, '2020-02-07', 3.83, 2.00, 212.53, 2);
INSERT INTO `tb_consume` VALUES (642, '2020-02-08', 9.73, 5.08, 207.45, 2);
INSERT INTO `tb_consume` VALUES (643, '2020-02-09', 8.70, 4.54, 202.91, 2);
INSERT INTO `tb_consume` VALUES (644, '2020-02-10', 8.41, 4.39, 198.52, 2);
INSERT INTO `tb_consume` VALUES (645, '2020-02-11', 4.76, 2.49, 196.03, 2);
INSERT INTO `tb_consume` VALUES (646, '2020-02-12', 3.10, 1.62, 194.41, 2);
INSERT INTO `tb_consume` VALUES (647, '2020-02-13', 6.66, 3.48, 190.93, 2);
INSERT INTO `tb_consume` VALUES (648, '2020-02-14', 1.15, 0.60, 190.33, 2);
INSERT INTO `tb_consume` VALUES (649, '2020-02-15', 1.79, 0.94, 189.39, 2);
INSERT INTO `tb_consume` VALUES (650, '2020-02-16', 6.84, 3.57, 185.82, 2);
INSERT INTO `tb_consume` VALUES (651, '2020-02-17', 9.76, 5.10, 180.72, 2);
INSERT INTO `tb_consume` VALUES (652, '2020-02-18', 6.01, 3.14, 177.58, 2);
INSERT INTO `tb_consume` VALUES (653, '2020-02-19', 9.27, 4.84, 172.74, 2);
INSERT INTO `tb_consume` VALUES (654, '2020-02-20', 3.78, 1.97, 170.77, 2);
INSERT INTO `tb_consume` VALUES (655, '2020-02-21', 5.81, 3.04, 167.73, 2);
INSERT INTO `tb_consume` VALUES (656, '2020-02-22', 3.13, 1.64, 166.09, 2);
INSERT INTO `tb_consume` VALUES (657, '2020-02-23', 9.19, 4.80, 161.29, 2);
INSERT INTO `tb_consume` VALUES (658, '2020-02-24', 6.42, 3.35, 157.94, 2);
INSERT INTO `tb_consume` VALUES (659, '2020-02-25', 4.20, 2.19, 155.75, 2);
INSERT INTO `tb_consume` VALUES (660, '2020-02-26', 1.69, 0.88, 154.87, 2);
INSERT INTO `tb_consume` VALUES (661, '2020-02-27', 3.02, 1.58, 153.29, 2);
INSERT INTO `tb_consume` VALUES (662, '2020-02-28', 8.96, 4.68, 148.61, 2);
INSERT INTO `tb_consume` VALUES (663, '2020-02-29', 6.44, 3.36, 145.25, 2);
INSERT INTO `tb_consume` VALUES (664, '2020-03-01', 5.10, 2.66, 142.59, 2);
INSERT INTO `tb_consume` VALUES (665, '2020-03-02', 9.56, 4.99, 137.60, 2);
INSERT INTO `tb_consume` VALUES (666, '2020-03-03', 3.30, 1.72, 135.88, 2);
INSERT INTO `tb_consume` VALUES (667, '2020-03-04', 8.92, 4.66, 131.22, 2);
INSERT INTO `tb_consume` VALUES (668, '2020-03-05', 6.37, 3.33, 127.89, 2);
INSERT INTO `tb_consume` VALUES (669, '2020-03-06', 6.08, 3.18, 124.71, 2);
INSERT INTO `tb_consume` VALUES (670, '2020-03-07', 9.98, 5.21, 119.50, 2);
INSERT INTO `tb_consume` VALUES (671, '2020-03-08', 4.37, 2.28, 117.22, 2);
INSERT INTO `tb_consume` VALUES (672, '2020-03-09', 9.96, 5.20, 112.02, 2);
INSERT INTO `tb_consume` VALUES (673, '2020-03-10', 7.21, 3.77, 108.25, 2);
INSERT INTO `tb_consume` VALUES (674, '2020-03-11', 0.80, 0.42, 107.83, 2);
INSERT INTO `tb_consume` VALUES (675, '2020-03-12', 1.49, 0.78, 107.05, 2);
INSERT INTO `tb_consume` VALUES (676, '2020-03-13', 6.01, 3.14, 103.91, 2);
INSERT INTO `tb_consume` VALUES (677, '2020-03-14', 1.36, 0.71, 103.20, 2);
INSERT INTO `tb_consume` VALUES (678, '2020-03-15', 0.27, 0.14, 103.06, 2);
INSERT INTO `tb_consume` VALUES (679, '2020-03-16', 4.16, 2.17, 100.89, 2);
INSERT INTO `tb_consume` VALUES (680, '2020-01-01', 9.56, 4.99, 295.01, 3);
INSERT INTO `tb_consume` VALUES (681, '2020-01-02', 5.19, 2.71, 292.30, 3);
INSERT INTO `tb_consume` VALUES (682, '2020-01-03', 3.17, 1.66, 290.64, 3);
INSERT INTO `tb_consume` VALUES (683, '2020-01-04', 8.75, 4.57, 286.07, 3);
INSERT INTO `tb_consume` VALUES (684, '2020-01-05', 5.35, 2.79, 283.28, 3);
INSERT INTO `tb_consume` VALUES (685, '2020-01-06', 6.17, 3.22, 280.06, 3);
INSERT INTO `tb_consume` VALUES (686, '2020-01-07', 2.73, 1.43, 278.63, 3);
INSERT INTO `tb_consume` VALUES (687, '2020-01-08', 9.61, 5.02, 273.61, 3);
INSERT INTO `tb_consume` VALUES (688, '2020-01-09', 8.96, 4.68, 268.93, 3);
INSERT INTO `tb_consume` VALUES (689, '2020-01-10', 8.41, 4.39, 264.54, 3);
INSERT INTO `tb_consume` VALUES (690, '2020-01-11', 8.19, 4.28, 260.26, 3);
INSERT INTO `tb_consume` VALUES (691, '2020-01-12', 0.19, 0.10, 260.16, 3);
INSERT INTO `tb_consume` VALUES (692, '2020-01-13', 5.76, 3.01, 257.15, 3);
INSERT INTO `tb_consume` VALUES (693, '2020-01-14', 0.44, 0.23, 256.92, 3);
INSERT INTO `tb_consume` VALUES (694, '2020-01-15', 8.88, 4.64, 252.28, 3);
INSERT INTO `tb_consume` VALUES (695, '2020-01-16', 8.96, 4.68, 247.60, 3);
INSERT INTO `tb_consume` VALUES (696, '2020-01-17', 7.59, 3.97, 243.63, 3);
INSERT INTO `tb_consume` VALUES (697, '2020-01-18', 9.30, 4.86, 238.77, 3);
INSERT INTO `tb_consume` VALUES (698, '2020-01-19', 3.21, 1.68, 237.09, 3);
INSERT INTO `tb_consume` VALUES (699, '2020-01-20', 8.46, 4.42, 232.67, 3);
INSERT INTO `tb_consume` VALUES (700, '2020-01-21', 1.92, 1.00, 231.67, 3);
INSERT INTO `tb_consume` VALUES (701, '2020-01-22', 7.51, 3.92, 227.75, 3);
INSERT INTO `tb_consume` VALUES (702, '2020-01-23', 3.23, 1.69, 226.06, 3);
INSERT INTO `tb_consume` VALUES (703, '2020-01-24', 5.45, 2.85, 223.21, 3);
INSERT INTO `tb_consume` VALUES (704, '2020-01-25', 8.75, 4.57, 218.64, 3);
INSERT INTO `tb_consume` VALUES (705, '2020-01-26', 8.24, 4.30, 214.34, 3);
INSERT INTO `tb_consume` VALUES (706, '2020-01-27', 6.23, 3.25, 211.09, 3);
INSERT INTO `tb_consume` VALUES (707, '2020-01-28', 4.13, 2.16, 208.93, 3);
INSERT INTO `tb_consume` VALUES (708, '2020-01-29', 7.80, 4.07, 204.86, 3);
INSERT INTO `tb_consume` VALUES (709, '2020-01-30', 4.41, 2.30, 202.56, 3);
INSERT INTO `tb_consume` VALUES (710, '2020-01-31', 2.41, 1.26, 201.30, 3);
INSERT INTO `tb_consume` VALUES (711, '2020-02-01', 5.12, 2.67, 198.63, 3);
INSERT INTO `tb_consume` VALUES (712, '2020-02-02', 4.05, 2.12, 196.51, 3);
INSERT INTO `tb_consume` VALUES (713, '2020-02-03', 5.49, 2.87, 193.64, 3);
INSERT INTO `tb_consume` VALUES (714, '2020-02-04', 7.29, 3.81, 189.83, 3);
INSERT INTO `tb_consume` VALUES (715, '2020-02-05', 1.82, 0.95, 188.88, 3);
INSERT INTO `tb_consume` VALUES (716, '2020-02-06', 1.58, 0.83, 188.05, 3);
INSERT INTO `tb_consume` VALUES (717, '2020-02-07', 9.87, 5.16, 182.89, 3);
INSERT INTO `tb_consume` VALUES (718, '2020-02-08', 9.27, 4.84, 178.05, 3);
INSERT INTO `tb_consume` VALUES (719, '2020-02-09', 0.33, 0.17, 177.88, 3);
INSERT INTO `tb_consume` VALUES (720, '2020-02-10', 3.20, 1.67, 176.21, 3);
INSERT INTO `tb_consume` VALUES (721, '2020-02-11', 9.18, 4.80, 171.41, 3);
INSERT INTO `tb_consume` VALUES (722, '2020-02-12', 2.78, 1.45, 169.96, 3);
INSERT INTO `tb_consume` VALUES (723, '2020-02-13', 2.52, 1.32, 168.64, 3);
INSERT INTO `tb_consume` VALUES (724, '2020-02-14', 8.23, 4.30, 164.34, 3);
INSERT INTO `tb_consume` VALUES (725, '2020-02-15', 7.66, 4.00, 160.34, 3);
INSERT INTO `tb_consume` VALUES (726, '2020-02-16', 1.74, 0.91, 159.43, 3);
INSERT INTO `tb_consume` VALUES (727, '2020-02-17', 6.75, 3.53, 155.90, 3);
INSERT INTO `tb_consume` VALUES (728, '2020-02-18', 1.37, 0.72, 155.18, 3);
INSERT INTO `tb_consume` VALUES (729, '2020-02-19', 9.86, 5.15, 150.03, 3);
INSERT INTO `tb_consume` VALUES (730, '2020-02-20', 8.55, 4.47, 145.56, 3);
INSERT INTO `tb_consume` VALUES (731, '2020-02-21', 9.31, 4.86, 140.70, 3);
INSERT INTO `tb_consume` VALUES (732, '2020-02-22', 0.73, 0.38, 140.32, 3);
INSERT INTO `tb_consume` VALUES (733, '2020-02-23', 1.16, 0.61, 139.71, 3);
INSERT INTO `tb_consume` VALUES (734, '2020-02-24', 0.48, 0.25, 139.46, 3);
INSERT INTO `tb_consume` VALUES (735, '2020-02-25', 5.99, 3.13, 136.33, 3);
INSERT INTO `tb_consume` VALUES (736, '2020-02-26', 6.93, 3.62, 132.71, 3);
INSERT INTO `tb_consume` VALUES (737, '2020-02-27', 9.12, 4.76, 127.95, 3);
INSERT INTO `tb_consume` VALUES (738, '2020-02-28', 4.27, 2.23, 125.72, 3);
INSERT INTO `tb_consume` VALUES (739, '2020-02-29', 0.79, 0.41, 125.31, 3);
INSERT INTO `tb_consume` VALUES (740, '2020-03-01', 1.81, 0.95, 124.36, 3);
INSERT INTO `tb_consume` VALUES (741, '2020-03-02', 0.02, 0.01, 124.35, 3);
INSERT INTO `tb_consume` VALUES (742, '2020-03-03', 1.73, 0.90, 123.45, 3);
INSERT INTO `tb_consume` VALUES (743, '2020-03-04', 8.12, 4.24, 119.21, 3);
INSERT INTO `tb_consume` VALUES (744, '2020-03-05', 9.04, 4.72, 114.49, 3);
INSERT INTO `tb_consume` VALUES (745, '2020-03-06', 2.62, 1.37, 113.12, 3);
INSERT INTO `tb_consume` VALUES (746, '2020-03-07', 2.18, 1.14, 111.98, 3);
INSERT INTO `tb_consume` VALUES (747, '2020-03-08', 8.75, 4.57, 107.41, 3);
INSERT INTO `tb_consume` VALUES (748, '2020-03-09', 4.07, 2.13, 105.28, 3);
INSERT INTO `tb_consume` VALUES (749, '2020-03-10', 5.11, 2.67, 102.61, 3);
INSERT INTO `tb_consume` VALUES (750, '2020-03-11', 9.81, 5.12, 97.49, 3);
INSERT INTO `tb_consume` VALUES (751, '2020-03-12', 9.16, 4.79, 92.70, 3);
INSERT INTO `tb_consume` VALUES (752, '2020-03-13', 1.33, 0.69, 92.01, 3);
INSERT INTO `tb_consume` VALUES (753, '2020-03-14', 8.25, 4.31, 87.70, 3);
INSERT INTO `tb_consume` VALUES (754, '2020-03-15', 3.07, 1.60, 86.10, 3);
INSERT INTO `tb_consume` VALUES (755, '2020-03-16', 5.02, 2.62, 83.48, 3);
INSERT INTO `tb_consume` VALUES (756, '2020-01-01', 2.01, 1.05, 298.95, 4);
INSERT INTO `tb_consume` VALUES (757, '2020-01-02', 7.28, 3.80, 295.15, 4);
INSERT INTO `tb_consume` VALUES (758, '2020-01-03', 9.18, 4.80, 290.35, 4);
INSERT INTO `tb_consume` VALUES (759, '2020-01-04', 0.46, 0.24, 290.11, 4);
INSERT INTO `tb_consume` VALUES (760, '2020-01-05', 2.39, 1.25, 288.86, 4);
INSERT INTO `tb_consume` VALUES (761, '2020-01-06', 9.77, 5.10, 283.76, 4);
INSERT INTO `tb_consume` VALUES (762, '2020-01-07', 7.21, 3.77, 279.99, 4);
INSERT INTO `tb_consume` VALUES (763, '2020-01-08', 8.68, 4.53, 275.46, 4);
INSERT INTO `tb_consume` VALUES (764, '2020-01-09', 3.07, 1.60, 273.86, 4);
INSERT INTO `tb_consume` VALUES (765, '2020-01-10', 0.26, 0.14, 273.72, 4);
INSERT INTO `tb_consume` VALUES (766, '2020-01-11', 4.40, 2.30, 271.42, 4);
INSERT INTO `tb_consume` VALUES (767, '2020-01-12', 2.53, 1.32, 270.10, 4);
INSERT INTO `tb_consume` VALUES (768, '2020-01-13', 0.25, 0.13, 269.97, 4);
INSERT INTO `tb_consume` VALUES (769, '2020-01-14', 2.54, 1.33, 268.64, 4);
INSERT INTO `tb_consume` VALUES (770, '2020-01-15', 5.23, 2.73, 265.91, 4);
INSERT INTO `tb_consume` VALUES (771, '2020-01-16', 5.37, 2.81, 263.10, 4);
INSERT INTO `tb_consume` VALUES (772, '2020-01-17', 9.32, 4.87, 258.23, 4);
INSERT INTO `tb_consume` VALUES (773, '2020-01-18', 8.34, 4.36, 253.87, 4);
INSERT INTO `tb_consume` VALUES (774, '2020-01-19', 0.02, 0.01, 253.86, 4);
INSERT INTO `tb_consume` VALUES (775, '2020-01-20', 6.80, 3.55, 250.31, 4);
INSERT INTO `tb_consume` VALUES (776, '2020-01-21', 3.79, 1.98, 248.33, 4);
INSERT INTO `tb_consume` VALUES (777, '2020-01-22', 2.48, 1.30, 247.03, 4);
INSERT INTO `tb_consume` VALUES (778, '2020-01-23', 4.71, 2.46, 244.57, 4);
INSERT INTO `tb_consume` VALUES (779, '2020-01-24', 1.45, 0.76, 243.81, 4);
INSERT INTO `tb_consume` VALUES (780, '2020-01-25', 9.88, 5.16, 238.65, 4);
INSERT INTO `tb_consume` VALUES (781, '2020-01-26', 2.04, 1.07, 237.58, 4);
INSERT INTO `tb_consume` VALUES (782, '2020-01-27', 6.87, 3.59, 233.99, 4);
INSERT INTO `tb_consume` VALUES (783, '2020-01-28', 3.07, 1.60, 232.39, 4);
INSERT INTO `tb_consume` VALUES (784, '2020-01-29', 9.52, 4.97, 227.42, 4);
INSERT INTO `tb_consume` VALUES (785, '2020-01-30', 5.02, 2.62, 224.80, 4);
INSERT INTO `tb_consume` VALUES (786, '2020-01-31', 9.90, 5.17, 219.63, 4);
INSERT INTO `tb_consume` VALUES (787, '2020-02-01', 5.86, 3.06, 216.57, 4);
INSERT INTO `tb_consume` VALUES (788, '2020-02-02', 0.60, 0.31, 216.26, 4);
INSERT INTO `tb_consume` VALUES (789, '2020-02-03', 2.54, 1.33, 214.93, 4);
INSERT INTO `tb_consume` VALUES (790, '2020-02-04', 9.77, 5.10, 209.83, 4);
INSERT INTO `tb_consume` VALUES (791, '2020-02-05', 7.36, 3.84, 205.99, 4);
INSERT INTO `tb_consume` VALUES (792, '2020-02-06', 3.43, 1.79, 204.20, 4);
INSERT INTO `tb_consume` VALUES (793, '2020-02-07', 7.78, 4.06, 200.14, 4);
INSERT INTO `tb_consume` VALUES (794, '2020-02-08', 5.57, 2.91, 197.23, 4);
INSERT INTO `tb_consume` VALUES (795, '2020-02-09', 3.77, 1.97, 195.26, 4);
INSERT INTO `tb_consume` VALUES (796, '2020-02-10', 4.98, 2.60, 192.66, 4);
INSERT INTO `tb_consume` VALUES (797, '2020-02-11', 5.03, 2.63, 190.03, 4);
INSERT INTO `tb_consume` VALUES (798, '2020-02-12', 3.78, 1.97, 188.06, 4);
INSERT INTO `tb_consume` VALUES (799, '2020-02-13', 3.03, 1.58, 186.48, 4);
INSERT INTO `tb_consume` VALUES (800, '2020-02-14', 0.98, 0.51, 185.97, 4);
INSERT INTO `tb_consume` VALUES (801, '2020-02-15', 1.24, 0.65, 185.32, 4);
INSERT INTO `tb_consume` VALUES (802, '2020-02-16', 5.99, 3.13, 182.19, 4);
INSERT INTO `tb_consume` VALUES (803, '2020-02-17', 8.52, 4.45, 177.74, 4);
INSERT INTO `tb_consume` VALUES (804, '2020-02-18', 0.92, 0.48, 177.26, 4);
INSERT INTO `tb_consume` VALUES (805, '2020-02-19', 4.12, 2.15, 175.11, 4);
INSERT INTO `tb_consume` VALUES (806, '2020-02-20', 5.56, 2.90, 172.21, 4);
INSERT INTO `tb_consume` VALUES (807, '2020-02-21', 4.65, 2.43, 169.78, 4);
INSERT INTO `tb_consume` VALUES (808, '2020-02-22', 9.46, 4.94, 164.84, 4);
INSERT INTO `tb_consume` VALUES (809, '2020-02-23', 0.03, 0.02, 164.82, 4);
INSERT INTO `tb_consume` VALUES (810, '2020-02-24', 8.98, 4.69, 160.13, 4);
INSERT INTO `tb_consume` VALUES (811, '2020-02-25', 1.86, 0.97, 159.16, 4);
INSERT INTO `tb_consume` VALUES (812, '2020-02-26', 6.38, 3.33, 155.83, 4);
INSERT INTO `tb_consume` VALUES (813, '2020-02-27', 0.57, 0.30, 155.53, 4);
INSERT INTO `tb_consume` VALUES (814, '2020-02-28', 7.66, 4.00, 151.53, 4);
INSERT INTO `tb_consume` VALUES (815, '2020-02-29', 2.89, 1.51, 150.02, 4);
INSERT INTO `tb_consume` VALUES (816, '2020-03-01', 4.97, 2.60, 147.42, 4);
INSERT INTO `tb_consume` VALUES (817, '2020-03-02', 4.69, 2.45, 144.97, 4);
INSERT INTO `tb_consume` VALUES (818, '2020-03-03', 2.47, 1.29, 143.68, 4);
INSERT INTO `tb_consume` VALUES (819, '2020-03-04', 2.00, 1.04, 142.64, 4);
INSERT INTO `tb_consume` VALUES (820, '2020-03-05', 3.28, 1.71, 140.93, 4);
INSERT INTO `tb_consume` VALUES (821, '2020-03-06', 9.86, 5.15, 135.78, 4);
INSERT INTO `tb_consume` VALUES (822, '2020-03-07', 7.77, 4.06, 131.72, 4);
INSERT INTO `tb_consume` VALUES (823, '2020-03-08', 5.70, 2.98, 128.74, 4);
INSERT INTO `tb_consume` VALUES (824, '2020-03-09', 2.71, 1.42, 127.32, 4);
INSERT INTO `tb_consume` VALUES (825, '2020-03-10', 6.80, 3.55, 123.77, 4);
INSERT INTO `tb_consume` VALUES (826, '2020-03-11', 5.50, 2.87, 120.90, 4);
INSERT INTO `tb_consume` VALUES (827, '2020-03-12', 3.39, 1.77, 119.13, 4);
INSERT INTO `tb_consume` VALUES (828, '2020-03-13', 1.41, 0.74, 118.39, 4);
INSERT INTO `tb_consume` VALUES (829, '2020-03-14', 3.25, 1.70, 116.69, 4);
INSERT INTO `tb_consume` VALUES (830, '2020-03-15', 9.52, 4.97, 111.72, 4);
INSERT INTO `tb_consume` VALUES (831, '2020-03-16', 1.44, 0.75, 110.97, 4);
INSERT INTO `tb_consume` VALUES (832, '2020-01-01', 5.25, 2.74, 297.26, 5);
INSERT INTO `tb_consume` VALUES (833, '2020-01-02', 1.81, 0.95, 296.31, 5);
INSERT INTO `tb_consume` VALUES (834, '2020-01-03', 7.41, 3.87, 292.44, 5);
INSERT INTO `tb_consume` VALUES (835, '2020-01-04', 2.36, 1.23, 291.21, 5);
INSERT INTO `tb_consume` VALUES (836, '2020-01-05', 7.58, 3.96, 287.25, 5);
INSERT INTO `tb_consume` VALUES (837, '2020-01-06', 7.04, 3.68, 283.57, 5);
INSERT INTO `tb_consume` VALUES (838, '2020-01-07', 8.63, 4.51, 279.06, 5);
INSERT INTO `tb_consume` VALUES (839, '2020-01-08', 7.90, 4.13, 274.93, 5);
INSERT INTO `tb_consume` VALUES (840, '2020-01-09', 7.89, 4.12, 270.81, 5);
INSERT INTO `tb_consume` VALUES (841, '2020-01-10', 6.68, 3.49, 267.32, 5);
INSERT INTO `tb_consume` VALUES (842, '2020-01-11', 8.78, 4.59, 262.73, 5);
INSERT INTO `tb_consume` VALUES (843, '2020-01-12', 3.65, 1.91, 260.82, 5);
INSERT INTO `tb_consume` VALUES (844, '2020-01-13', 5.79, 3.02, 257.80, 5);
INSERT INTO `tb_consume` VALUES (845, '2020-01-14', 9.93, 5.19, 252.61, 5);
INSERT INTO `tb_consume` VALUES (846, '2020-01-15', 4.26, 2.23, 250.38, 5);
INSERT INTO `tb_consume` VALUES (847, '2020-01-16', 1.81, 0.95, 249.43, 5);
INSERT INTO `tb_consume` VALUES (848, '2020-01-17', 9.29, 4.85, 244.58, 5);
INSERT INTO `tb_consume` VALUES (849, '2020-01-18', 4.75, 2.48, 242.10, 5);
INSERT INTO `tb_consume` VALUES (850, '2020-01-19', 1.00, 0.52, 241.58, 5);
INSERT INTO `tb_consume` VALUES (851, '2020-01-20', 2.81, 1.47, 240.11, 5);
INSERT INTO `tb_consume` VALUES (852, '2020-01-21', 7.00, 3.66, 236.45, 5);
INSERT INTO `tb_consume` VALUES (853, '2020-01-22', 6.40, 3.34, 233.11, 5);
INSERT INTO `tb_consume` VALUES (854, '2020-01-23', 2.39, 1.25, 231.86, 5);
INSERT INTO `tb_consume` VALUES (855, '2020-01-24', 5.57, 2.91, 228.95, 5);
INSERT INTO `tb_consume` VALUES (856, '2020-01-25', 1.55, 0.81, 228.14, 5);
INSERT INTO `tb_consume` VALUES (857, '2020-01-26', 9.10, 4.75, 223.39, 5);
INSERT INTO `tb_consume` VALUES (858, '2020-01-27', 2.24, 1.17, 222.22, 5);
INSERT INTO `tb_consume` VALUES (859, '2020-01-28', 4.88, 2.55, 219.67, 5);
INSERT INTO `tb_consume` VALUES (860, '2020-01-29', 7.19, 3.76, 215.91, 5);
INSERT INTO `tb_consume` VALUES (861, '2020-01-30', 5.53, 2.89, 213.02, 5);
INSERT INTO `tb_consume` VALUES (862, '2020-01-31', 2.78, 1.45, 211.57, 5);
INSERT INTO `tb_consume` VALUES (863, '2020-02-01', 3.32, 1.73, 209.84, 5);
INSERT INTO `tb_consume` VALUES (864, '2020-02-02', 3.59, 1.88, 207.96, 5);
INSERT INTO `tb_consume` VALUES (865, '2020-02-03', 4.29, 2.24, 205.72, 5);
INSERT INTO `tb_consume` VALUES (866, '2020-02-04', 2.22, 1.16, 204.56, 5);
INSERT INTO `tb_consume` VALUES (867, '2020-02-05', 6.45, 3.37, 201.19, 5);
INSERT INTO `tb_consume` VALUES (868, '2020-02-06', 2.19, 1.14, 200.05, 5);
INSERT INTO `tb_consume` VALUES (869, '2020-02-07', 3.57, 1.86, 198.19, 5);
INSERT INTO `tb_consume` VALUES (870, '2020-02-08', 9.99, 5.22, 192.97, 5);
INSERT INTO `tb_consume` VALUES (871, '2020-02-09', 7.64, 3.99, 188.98, 5);
INSERT INTO `tb_consume` VALUES (872, '2020-02-10', 4.47, 2.34, 186.64, 5);
INSERT INTO `tb_consume` VALUES (873, '2020-02-11', 4.17, 2.18, 184.46, 5);
INSERT INTO `tb_consume` VALUES (874, '2020-02-12', 2.41, 1.26, 183.20, 5);
INSERT INTO `tb_consume` VALUES (875, '2020-02-13', 2.30, 1.20, 182.00, 5);
INSERT INTO `tb_consume` VALUES (876, '2020-02-13', 8.14, 4.25, 182.39, 5);
INSERT INTO `tb_consume` VALUES (877, '2020-02-14', 7.69, 4.02, 177.98, 5);
INSERT INTO `tb_consume` VALUES (878, '2020-02-14', 9.10, 4.75, 177.64, 5);
INSERT INTO `tb_consume` VALUES (879, '2020-02-15', 4.92, 2.57, 175.41, 5);
INSERT INTO `tb_consume` VALUES (880, '2020-02-16', 6.42, 3.35, 172.06, 5);
INSERT INTO `tb_consume` VALUES (881, '2020-02-16', 1.87, 0.98, 176.66, 5);
INSERT INTO `tb_consume` VALUES (882, '2020-02-17', 6.95, 3.63, 168.43, 5);
INSERT INTO `tb_consume` VALUES (883, '2020-02-17', 0.26, 0.14, 176.52, 5);
INSERT INTO `tb_consume` VALUES (884, '2020-02-18', 8.59, 4.49, 163.94, 5);
INSERT INTO `tb_consume` VALUES (885, '2020-02-18', 4.60, 2.40, 174.12, 5);
INSERT INTO `tb_consume` VALUES (886, '2020-02-19', 9.56, 4.99, 158.95, 5);
INSERT INTO `tb_consume` VALUES (887, '2020-02-19', 2.58, 1.35, 172.77, 5);
INSERT INTO `tb_consume` VALUES (888, '2020-02-20', 9.39, 4.91, 154.04, 5);
INSERT INTO `tb_consume` VALUES (889, '2020-02-20', 7.48, 3.91, 168.86, 5);
INSERT INTO `tb_consume` VALUES (890, '2020-02-21', 5.35, 2.79, 151.25, 5);
INSERT INTO `tb_consume` VALUES (891, '2020-02-21', 9.07, 4.74, 164.12, 5);
INSERT INTO `tb_consume` VALUES (892, '2020-02-22', 0.94, 0.49, 150.76, 5);
INSERT INTO `tb_consume` VALUES (893, '2020-02-22', 2.34, 1.22, 162.90, 5);
INSERT INTO `tb_consume` VALUES (894, '2020-02-23', 9.56, 4.99, 145.77, 5);
INSERT INTO `tb_consume` VALUES (895, '2020-02-23', 8.78, 4.59, 158.31, 5);
INSERT INTO `tb_consume` VALUES (896, '2020-02-24', 2.02, 1.06, 144.71, 5);
INSERT INTO `tb_consume` VALUES (897, '2020-02-24', 9.57, 5.00, 153.31, 5);
INSERT INTO `tb_consume` VALUES (898, '2020-02-25', 3.99, 2.08, 142.63, 5);
INSERT INTO `tb_consume` VALUES (899, '2020-02-25', 9.85, 5.15, 148.16, 5);
INSERT INTO `tb_consume` VALUES (900, '2020-02-26', 0.59, 0.31, 142.32, 5);
INSERT INTO `tb_consume` VALUES (901, '2020-02-26', 6.14, 3.21, 144.95, 5);
INSERT INTO `tb_consume` VALUES (902, '2020-02-27', 2.61, 1.36, 140.96, 5);
INSERT INTO `tb_consume` VALUES (903, '2020-02-27', 9.54, 4.98, 139.97, 5);
INSERT INTO `tb_consume` VALUES (904, '2020-02-28', 0.60, 0.31, 140.65, 5);
INSERT INTO `tb_consume` VALUES (905, '2020-02-28', 8.08, 4.22, 135.75, 5);
INSERT INTO `tb_consume` VALUES (906, '2020-02-29', 5.62, 2.94, 137.71, 5);
INSERT INTO `tb_consume` VALUES (907, '2020-02-29', 5.86, 3.06, 132.69, 5);
INSERT INTO `tb_consume` VALUES (908, '2020-03-01', 3.57, 1.86, 130.83, 5);
INSERT INTO `tb_consume` VALUES (909, '2020-03-02', 0.24, 0.13, 130.70, 5);
INSERT INTO `tb_consume` VALUES (910, '2020-03-03', 9.80, 5.12, 125.58, 5);
INSERT INTO `tb_consume` VALUES (911, '2020-03-04', 8.07, 4.22, 121.36, 5);
INSERT INTO `tb_consume` VALUES (912, '2020-03-05', 0.97, 0.51, 120.85, 5);
INSERT INTO `tb_consume` VALUES (913, '2020-03-06', 2.20, 1.15, 119.70, 5);
INSERT INTO `tb_consume` VALUES (914, '2020-03-07', 4.40, 2.30, 117.40, 5);
INSERT INTO `tb_consume` VALUES (915, '2020-03-08', 4.60, 2.40, 115.00, 5);
INSERT INTO `tb_consume` VALUES (916, '2020-03-09', 4.80, 2.51, 112.49, 5);
INSERT INTO `tb_consume` VALUES (917, '2020-03-10', 3.31, 1.73, 110.76, 5);
INSERT INTO `tb_consume` VALUES (918, '2020-03-11', 7.27, 3.80, 106.96, 5);
INSERT INTO `tb_consume` VALUES (919, '2020-03-12', 1.12, 0.59, 106.37, 5);
INSERT INTO `tb_consume` VALUES (920, '2020-03-13', 7.97, 4.16, 102.21, 5);
INSERT INTO `tb_consume` VALUES (921, '2020-03-14', 3.29, 1.72, 100.49, 5);
INSERT INTO `tb_consume` VALUES (922, '2020-03-15', 6.90, 3.60, 96.89, 5);
INSERT INTO `tb_consume` VALUES (923, '2020-03-16', 2.70, 1.41, 95.48, 5);
INSERT INTO `tb_consume` VALUES (924, '2020-01-01', 7.59, 3.97, 296.03, 11);
INSERT INTO `tb_consume` VALUES (925, '2020-01-02', 4.36, 2.28, 293.75, 11);
INSERT INTO `tb_consume` VALUES (926, '2020-01-03', 7.41, 3.87, 289.88, 11);
INSERT INTO `tb_consume` VALUES (927, '2020-01-04', 1.34, 0.70, 289.18, 11);
INSERT INTO `tb_consume` VALUES (928, '2020-01-05', 9.44, 4.93, 284.25, 11);
INSERT INTO `tb_consume` VALUES (929, '2020-01-06', 6.62, 3.46, 280.79, 11);
INSERT INTO `tb_consume` VALUES (930, '2020-01-07', 3.44, 1.80, 278.99, 11);
INSERT INTO `tb_consume` VALUES (931, '2020-01-08', 6.72, 3.51, 275.48, 11);
INSERT INTO `tb_consume` VALUES (932, '2020-01-09', 2.76, 1.44, 274.04, 11);
INSERT INTO `tb_consume` VALUES (933, '2020-01-10', 5.82, 3.04, 271.00, 11);
INSERT INTO `tb_consume` VALUES (934, '2020-01-11', 8.62, 4.50, 266.50, 11);
INSERT INTO `tb_consume` VALUES (935, '2020-01-12', 8.49, 4.44, 262.06, 11);
INSERT INTO `tb_consume` VALUES (936, '2020-01-13', 6.70, 3.50, 258.56, 11);
INSERT INTO `tb_consume` VALUES (937, '2020-01-14', 0.19, 0.10, 258.46, 11);
INSERT INTO `tb_consume` VALUES (938, '2020-01-15', 5.10, 2.66, 255.80, 11);
INSERT INTO `tb_consume` VALUES (939, '2020-01-16', 0.50, 0.26, 255.54, 11);
INSERT INTO `tb_consume` VALUES (940, '2020-01-17', 1.72, 0.90, 254.64, 11);
INSERT INTO `tb_consume` VALUES (941, '2020-01-18', 5.28, 2.76, 251.88, 11);
INSERT INTO `tb_consume` VALUES (942, '2020-01-19', 6.00, 3.13, 248.75, 11);
INSERT INTO `tb_consume` VALUES (943, '2020-01-20', 9.24, 4.83, 243.92, 11);
INSERT INTO `tb_consume` VALUES (944, '2020-01-21', 3.29, 1.72, 242.20, 11);
INSERT INTO `tb_consume` VALUES (945, '2020-01-22', 7.31, 3.82, 238.38, 11);
INSERT INTO `tb_consume` VALUES (946, '2020-01-23', 5.15, 2.69, 235.69, 11);
INSERT INTO `tb_consume` VALUES (947, '2020-01-24', 7.38, 3.86, 231.83, 11);
INSERT INTO `tb_consume` VALUES (948, '2020-01-25', 5.42, 2.83, 229.00, 11);
INSERT INTO `tb_consume` VALUES (949, '2020-01-26', 2.69, 1.41, 227.59, 11);
INSERT INTO `tb_consume` VALUES (950, '2020-01-27', 4.40, 2.30, 225.29, 11);
INSERT INTO `tb_consume` VALUES (951, '2020-01-28', 4.08, 2.13, 223.16, 11);
INSERT INTO `tb_consume` VALUES (952, '2020-01-29', 3.18, 1.66, 221.50, 11);
INSERT INTO `tb_consume` VALUES (953, '2020-01-30', 0.32, 0.17, 221.33, 11);
INSERT INTO `tb_consume` VALUES (954, '2020-01-31', 0.72, 0.38, 220.95, 11);
INSERT INTO `tb_consume` VALUES (955, '2020-02-01', 3.09, 1.61, 219.34, 11);
INSERT INTO `tb_consume` VALUES (956, '2020-02-02', 6.39, 3.34, 216.00, 11);
INSERT INTO `tb_consume` VALUES (957, '2020-02-03', 1.67, 0.87, 215.13, 11);
INSERT INTO `tb_consume` VALUES (958, '2020-02-04', 3.63, 1.90, 213.23, 11);
INSERT INTO `tb_consume` VALUES (959, '2020-02-05', 0.23, 0.12, 213.11, 11);
INSERT INTO `tb_consume` VALUES (960, '2020-02-06', 0.11, 0.06, 213.05, 11);
INSERT INTO `tb_consume` VALUES (961, '2020-02-07', 0.44, 0.23, 212.82, 11);
INSERT INTO `tb_consume` VALUES (962, '2020-02-08', 6.90, 3.60, 209.22, 11);
INSERT INTO `tb_consume` VALUES (963, '2020-02-09', 7.38, 3.86, 205.36, 11);
INSERT INTO `tb_consume` VALUES (964, '2020-02-10', 6.84, 3.57, 201.79, 11);
INSERT INTO `tb_consume` VALUES (965, '2020-02-11', 2.66, 1.39, 200.40, 11);
INSERT INTO `tb_consume` VALUES (966, '2020-02-12', 0.72, 0.38, 200.02, 11);
INSERT INTO `tb_consume` VALUES (967, '2020-02-13', 0.38, 0.20, 199.82, 11);
INSERT INTO `tb_consume` VALUES (968, '2020-02-14', 1.39, 0.73, 199.09, 11);
INSERT INTO `tb_consume` VALUES (969, '2020-02-15', 2.38, 1.24, 197.85, 11);
INSERT INTO `tb_consume` VALUES (970, '2020-02-16', 0.98, 0.51, 197.34, 11);
INSERT INTO `tb_consume` VALUES (971, '2020-02-17', 3.68, 1.92, 195.42, 11);
INSERT INTO `tb_consume` VALUES (972, '2020-02-18', 3.17, 1.66, 193.76, 11);
INSERT INTO `tb_consume` VALUES (973, '2020-02-19', 6.64, 3.47, 190.29, 11);
INSERT INTO `tb_consume` VALUES (974, '2020-02-20', 5.88, 3.07, 187.22, 11);
INSERT INTO `tb_consume` VALUES (975, '2020-02-21', 5.73, 2.99, 184.23, 11);
INSERT INTO `tb_consume` VALUES (976, '2020-02-22', 9.80, 5.12, 179.11, 11);
INSERT INTO `tb_consume` VALUES (977, '2020-02-23', 9.64, 5.04, 174.07, 11);
INSERT INTO `tb_consume` VALUES (978, '2020-02-24', 8.70, 4.54, 169.53, 11);
INSERT INTO `tb_consume` VALUES (979, '2020-02-25', 7.62, 3.98, 165.55, 11);
INSERT INTO `tb_consume` VALUES (980, '2020-02-26', 2.81, 1.47, 164.08, 11);
INSERT INTO `tb_consume` VALUES (981, '2020-02-27', 6.53, 3.41, 160.67, 11);
INSERT INTO `tb_consume` VALUES (982, '2020-02-28', 0.71, 0.37, 160.30, 11);
INSERT INTO `tb_consume` VALUES (983, '2020-02-29', 1.25, 0.65, 159.65, 11);
INSERT INTO `tb_consume` VALUES (984, '2020-03-01', 4.71, 2.46, 157.19, 11);
INSERT INTO `tb_consume` VALUES (985, '2020-03-02', 4.84, 2.53, 154.66, 11);
INSERT INTO `tb_consume` VALUES (986, '2020-03-03', 8.32, 4.35, 150.31, 11);
INSERT INTO `tb_consume` VALUES (987, '2020-03-04', 8.05, 4.21, 146.10, 11);
INSERT INTO `tb_consume` VALUES (988, '2020-03-05', 9.81, 5.12, 140.98, 11);
INSERT INTO `tb_consume` VALUES (989, '2020-03-06', 4.09, 2.14, 138.84, 11);
INSERT INTO `tb_consume` VALUES (990, '2020-03-07', 1.86, 0.97, 137.87, 11);
INSERT INTO `tb_consume` VALUES (991, '2020-03-08', 0.33, 0.17, 137.70, 11);
INSERT INTO `tb_consume` VALUES (992, '2020-03-09', 0.72, 0.38, 137.32, 11);
INSERT INTO `tb_consume` VALUES (993, '2020-03-10', 1.83, 0.96, 136.36, 11);
INSERT INTO `tb_consume` VALUES (994, '2020-03-11', 7.20, 3.76, 132.60, 11);
INSERT INTO `tb_consume` VALUES (995, '2020-03-12', 1.74, 0.91, 131.69, 11);
INSERT INTO `tb_consume` VALUES (996, '2020-03-13', 5.70, 2.98, 128.71, 11);
INSERT INTO `tb_consume` VALUES (997, '2020-03-14', 7.41, 3.87, 124.84, 11);
INSERT INTO `tb_consume` VALUES (998, '2020-03-15', 5.85, 3.06, 121.78, 11);
INSERT INTO `tb_consume` VALUES (999, '2020-03-16', 7.82, 4.09, 117.69, 11);
INSERT INTO `tb_consume` VALUES (1000, '2020-03-17', 5.17, 2.70, 96.77, 1);
INSERT INTO `tb_consume` VALUES (1001, '2020-03-18', 1.42, 0.74, 96.03, 1);
INSERT INTO `tb_consume` VALUES (1002, '2020-03-19', 8.93, 4.67, 91.36, 1);
INSERT INTO `tb_consume` VALUES (1003, '2020-03-20', 5.66, 2.96, 88.40, 1);
INSERT INTO `tb_consume` VALUES (1004, '2020-03-21', 1.59, 0.83, 87.57, 1);
INSERT INTO `tb_consume` VALUES (1005, '2020-03-22', 5.26, 2.75, 84.82, 1);
INSERT INTO `tb_consume` VALUES (1006, '2020-03-17', 8.41, 4.39, 96.50, 2);
INSERT INTO `tb_consume` VALUES (1007, '2020-03-18', 9.35, 4.88, 91.62, 2);
INSERT INTO `tb_consume` VALUES (1008, '2020-03-19', 4.70, 2.46, 89.16, 2);
INSERT INTO `tb_consume` VALUES (1009, '2020-03-20', 3.11, 1.62, 87.54, 2);
INSERT INTO `tb_consume` VALUES (1010, '2020-03-21', 4.50, 2.35, 85.19, 2);
INSERT INTO `tb_consume` VALUES (1011, '2020-03-22', 7.92, 4.14, 81.05, 2);
INSERT INTO `tb_consume` VALUES (1012, '2020-03-17', 6.76, 3.53, 79.95, 3);
INSERT INTO `tb_consume` VALUES (1013, '2020-03-18', 6.62, 3.46, 76.49, 3);
INSERT INTO `tb_consume` VALUES (1014, '2020-03-19', 3.21, 1.68, 74.81, 3);
INSERT INTO `tb_consume` VALUES (1015, '2020-03-20', 9.60, 5.02, 69.79, 3);
INSERT INTO `tb_consume` VALUES (1016, '2020-03-21', 6.51, 3.40, 66.39, 3);
INSERT INTO `tb_consume` VALUES (1017, '2020-03-22', 5.62, 2.94, 63.45, 3);
INSERT INTO `tb_consume` VALUES (1018, '2020-03-17', 8.10, 4.23, 106.74, 4);
INSERT INTO `tb_consume` VALUES (1019, '2020-03-18', 5.51, 2.88, 103.86, 4);
INSERT INTO `tb_consume` VALUES (1020, '2020-03-19', 3.20, 1.67, 102.19, 4);
INSERT INTO `tb_consume` VALUES (1021, '2020-03-20', 9.50, 4.96, 97.23, 4);
INSERT INTO `tb_consume` VALUES (1022, '2020-03-21', 3.52, 1.84, 95.39, 4);
INSERT INTO `tb_consume` VALUES (1023, '2020-03-22', 5.98, 3.12, 92.27, 4);
INSERT INTO `tb_consume` VALUES (1024, '2020-03-17', 4.94, 2.58, 92.90, 5);
INSERT INTO `tb_consume` VALUES (1025, '2020-03-18', 1.33, 0.69, 92.21, 5);
INSERT INTO `tb_consume` VALUES (1026, '2020-03-19', 8.73, 4.56, 87.65, 5);
INSERT INTO `tb_consume` VALUES (1027, '2020-03-20', 4.36, 2.28, 85.37, 5);
INSERT INTO `tb_consume` VALUES (1028, '2020-03-21', 7.80, 4.07, 81.30, 5);
INSERT INTO `tb_consume` VALUES (1029, '2020-03-22', 9.50, 4.96, 76.34, 5);
INSERT INTO `tb_consume` VALUES (1030, '2020-03-17', 3.32, 1.73, 115.96, 11);
INSERT INTO `tb_consume` VALUES (1031, '2020-03-18', 0.06, 0.03, 115.93, 11);
INSERT INTO `tb_consume` VALUES (1032, '2020-03-19', 4.87, 2.54, 113.39, 11);
INSERT INTO `tb_consume` VALUES (1033, '2020-03-20', 8.61, 4.50, 108.89, 11);
INSERT INTO `tb_consume` VALUES (1034, '2020-03-21', 1.76, 0.92, 107.97, 11);
INSERT INTO `tb_consume` VALUES (1035, '2020-03-22', 6.69, 3.49, 104.48, 11);
INSERT INTO `tb_consume` VALUES (1036, '2020-03-23', 4.69, 2.45, 92.37, 1);
INSERT INTO `tb_consume` VALUES (1037, '2020-03-24', 0.82, 0.43, 91.94, 1);
INSERT INTO `tb_consume` VALUES (1038, '2020-03-25', 5.39, 2.82, 89.12, 1);
INSERT INTO `tb_consume` VALUES (1039, '2020-03-26', 2.87, 1.50, 87.62, 1);
INSERT INTO `tb_consume` VALUES (1040, '2020-03-27', 4.91, 2.56, 85.06, 1);
INSERT INTO `tb_consume` VALUES (1041, '2020-03-23', 3.31, 1.73, 79.32, 2);
INSERT INTO `tb_consume` VALUES (1042, '2020-03-24', 4.42, 2.31, 77.01, 2);
INSERT INTO `tb_consume` VALUES (1043, '2020-03-25', 8.69, 4.54, 72.47, 2);
INSERT INTO `tb_consume` VALUES (1044, '2020-03-26', 0.22, 0.11, 72.36, 2);
INSERT INTO `tb_consume` VALUES (1045, '2020-03-27', 4.16, 2.17, 70.19, 2);
INSERT INTO `tb_consume` VALUES (1046, '2020-03-23', 7.20, 3.76, 59.69, 3);
INSERT INTO `tb_consume` VALUES (1047, '2020-03-24', 0.37, 0.19, 59.50, 3);
INSERT INTO `tb_consume` VALUES (1048, '2020-03-25', 1.68, 0.88, 58.62, 3);
INSERT INTO `tb_consume` VALUES (1049, '2020-03-26', 2.63, 1.37, 57.25, 3);
INSERT INTO `tb_consume` VALUES (1050, '2020-03-27', 2.61, 1.36, 55.89, 3);
INSERT INTO `tb_consume` VALUES (1051, '2020-03-23', 7.84, 4.10, 88.17, 4);
INSERT INTO `tb_consume` VALUES (1052, '2020-03-24', 9.92, 5.18, 82.99, 4);
INSERT INTO `tb_consume` VALUES (1053, '2020-03-25', 1.20, 0.63, 82.36, 4);
INSERT INTO `tb_consume` VALUES (1054, '2020-03-26', 8.25, 4.31, 78.05, 4);
INSERT INTO `tb_consume` VALUES (1055, '2020-03-27', 9.98, 5.21, 72.84, 4);
INSERT INTO `tb_consume` VALUES (1056, '2020-03-23', 0.71, 0.37, 75.97, 5);
INSERT INTO `tb_consume` VALUES (1057, '2020-03-24', 2.36, 1.23, 74.74, 5);
INSERT INTO `tb_consume` VALUES (1058, '2020-03-25', 5.06, 2.64, 72.10, 5);
INSERT INTO `tb_consume` VALUES (1059, '2020-03-26', 0.57, 0.30, 71.80, 5);
INSERT INTO `tb_consume` VALUES (1060, '2020-03-27', 1.22, 0.64, 71.16, 5);
INSERT INTO `tb_consume` VALUES (1061, '2020-03-23', 2.31, 1.21, 103.27, 11);
INSERT INTO `tb_consume` VALUES (1062, '2020-03-24', 8.32, 4.35, 98.92, 11);
INSERT INTO `tb_consume` VALUES (1063, '2020-03-25', 8.08, 4.22, 94.70, 11);
INSERT INTO `tb_consume` VALUES (1064, '2020-03-26', 0.78, 0.41, 94.29, 11);
INSERT INTO `tb_consume` VALUES (1065, '2020-03-27', 9.00, 4.70, 89.59, 11);
INSERT INTO `tb_consume` VALUES (1066, '2020-03-28', 7.96, 4.16, 110.90, 1);
INSERT INTO `tb_consume` VALUES (1067, '2020-03-29', 6.31, 3.30, 107.60, 1);
INSERT INTO `tb_consume` VALUES (1068, '2020-03-30', 5.93, 3.10, 104.50, 1);
INSERT INTO `tb_consume` VALUES (1069, '2020-03-31', 8.32, 4.35, 100.15, 1);
INSERT INTO `tb_consume` VALUES (1070, '2020-03-28', 8.35, 4.36, 65.83, 2);
INSERT INTO `tb_consume` VALUES (1071, '2020-03-29', 2.92, 1.53, 64.30, 2);
INSERT INTO `tb_consume` VALUES (1072, '2020-03-30', 1.41, 0.74, 63.56, 2);
INSERT INTO `tb_consume` VALUES (1073, '2020-03-31', 5.55, 2.90, 60.66, 2);
INSERT INTO `tb_consume` VALUES (1074, '2020-03-28', 0.45, 0.24, 55.65, 3);
INSERT INTO `tb_consume` VALUES (1075, '2020-03-29', 2.52, 1.32, 54.33, 3);
INSERT INTO `tb_consume` VALUES (1076, '2020-03-30', 5.99, 3.13, 51.20, 3);
INSERT INTO `tb_consume` VALUES (1077, '2020-03-31', 1.74, 0.91, 50.29, 3);
INSERT INTO `tb_consume` VALUES (1078, '2020-03-28', 6.96, 3.64, 69.20, 4);
INSERT INTO `tb_consume` VALUES (1079, '2020-03-29', 5.42, 2.83, 66.37, 4);
INSERT INTO `tb_consume` VALUES (1080, '2020-03-30', 0.64, 0.33, 66.04, 4);
INSERT INTO `tb_consume` VALUES (1081, '2020-03-31', 6.20, 3.24, 62.80, 4);
INSERT INTO `tb_consume` VALUES (1082, '2020-03-28', 7.07, 3.69, 67.47, 5);
INSERT INTO `tb_consume` VALUES (1083, '2020-03-29', 5.03, 2.63, 64.84, 5);
INSERT INTO `tb_consume` VALUES (1084, '2020-03-30', 3.61, 1.89, 62.95, 5);
INSERT INTO `tb_consume` VALUES (1085, '2020-03-31', 7.15, 3.74, 59.21, 5);
INSERT INTO `tb_consume` VALUES (1086, '2020-03-28', 3.78, 1.97, 87.62, 11);
INSERT INTO `tb_consume` VALUES (1087, '2020-03-29', 3.30, 1.72, 85.90, 11);
INSERT INTO `tb_consume` VALUES (1088, '2020-03-30', 7.85, 4.10, 81.80, 11);
INSERT INTO `tb_consume` VALUES (1089, '2020-03-31', 9.60, 5.02, 76.78, 11);
INSERT INTO `tb_consume` VALUES (1090, '2020-04-01', 3.90, 2.04, 74.74, 11);
INSERT INTO `tb_consume` VALUES (1091, '2020-04-02', 1.37, 0.72, 74.02, 11);
INSERT INTO `tb_consume` VALUES (1092, '2020-04-03', 0.80, 0.42, 73.60, 11);
INSERT INTO `tb_consume` VALUES (1093, '2020-04-04', 9.64, 5.04, 68.56, 11);
INSERT INTO `tb_consume` VALUES (1094, '2020-04-05', 1.00, 0.52, 68.04, 11);
INSERT INTO `tb_consume` VALUES (1095, '2020-04-06', 3.96, 2.07, 65.97, 11);
INSERT INTO `tb_consume` VALUES (1096, '2020-04-07', 0.33, 0.17, 65.80, 11);
INSERT INTO `tb_consume` VALUES (1097, '2020-04-08', 3.34, 1.74, 64.06, 11);
INSERT INTO `tb_consume` VALUES (1098, '2020-04-09', 6.90, 3.60, 60.46, 11);
INSERT INTO `tb_consume` VALUES (1099, '2020-04-10', 1.97, 1.03, 59.43, 11);
INSERT INTO `tb_consume` VALUES (1100, '2020-04-11', 5.57, 2.91, 56.52, 11);
INSERT INTO `tb_consume` VALUES (1101, '2020-04-12', 6.69, 3.49, 53.03, 11);
INSERT INTO `tb_consume` VALUES (1102, '2020-04-13', 2.59, 1.35, 51.68, 11);
INSERT INTO `tb_consume` VALUES (1103, '2020-04-14', 3.90, 2.04, 49.64, 11);
INSERT INTO `tb_consume` VALUES (1104, '2020-04-15', 1.53, 0.80, 48.84, 11);
INSERT INTO `tb_consume` VALUES (1105, '2020-04-16', 8.66, 4.52, 44.32, 11);
INSERT INTO `tb_consume` VALUES (1106, '2020-04-17', 5.98, 3.12, 41.20, 11);
INSERT INTO `tb_consume` VALUES (1107, '2020-04-18', 9.00, 4.70, 36.50, 11);
INSERT INTO `tb_consume` VALUES (1108, '2020-04-19', 7.47, 3.90, 32.60, 11);
INSERT INTO `tb_consume` VALUES (1109, '2020-04-20', 8.05, 4.21, 28.39, 11);
INSERT INTO `tb_consume` VALUES (1110, '2020-04-21', 7.64, 3.99, 24.40, 11);
INSERT INTO `tb_consume` VALUES (1111, '2020-04-22', 9.51, 4.97, 19.43, 11);
INSERT INTO `tb_consume` VALUES (1112, '2020-04-23', 6.38, 3.33, 16.10, 11);
INSERT INTO `tb_consume` VALUES (1113, '2020-04-24', 7.68, 4.01, 12.09, 11);
INSERT INTO `tb_consume` VALUES (1114, '2020-04-25', 3.81, 1.99, 10.10, 11);
INSERT INTO `tb_consume` VALUES (1115, '2020-04-26', 1.55, 0.81, 9.29, 11);
INSERT INTO `tb_consume` VALUES (1116, '2020-04-27', 5.40, 2.82, 6.47, 11);
INSERT INTO `tb_consume` VALUES (1117, '2020-04-28', 2.87, 1.50, 4.97, 11);
INSERT INTO `tb_consume` VALUES (1118, '2020-04-29', 1.34, 0.70, 4.27, 11);
INSERT INTO `tb_consume` VALUES (1119, '2020-04-30', 5.53, 2.89, 1.38, 11);
INSERT INTO `tb_consume` VALUES (1120, '2020-04-01', 2.99, 1.56, 57.65, 5);
INSERT INTO `tb_consume` VALUES (1121, '2020-04-02', 4.67, 2.44, 55.21, 5);
INSERT INTO `tb_consume` VALUES (1122, '2020-04-03', 1.45, 0.76, 54.45, 5);
INSERT INTO `tb_consume` VALUES (1123, '2020-04-04', 6.15, 3.21, 51.24, 5);
INSERT INTO `tb_consume` VALUES (1124, '2020-04-05', 3.38, 1.77, 49.47, 5);
INSERT INTO `tb_consume` VALUES (1125, '2020-04-06', 9.77, 5.10, 44.37, 5);
INSERT INTO `tb_consume` VALUES (1126, '2020-04-07', 5.72, 2.99, 41.38, 5);
INSERT INTO `tb_consume` VALUES (1127, '2020-04-08', 2.04, 1.07, 40.31, 5);
INSERT INTO `tb_consume` VALUES (1128, '2020-04-09', 7.83, 4.09, 36.22, 5);
INSERT INTO `tb_consume` VALUES (1129, '2020-04-10', 5.99, 3.13, 33.09, 5);
INSERT INTO `tb_consume` VALUES (1130, '2020-04-11', 8.76, 4.58, 28.51, 5);
INSERT INTO `tb_consume` VALUES (1131, '2020-04-12', 5.04, 2.63, 25.88, 5);
INSERT INTO `tb_consume` VALUES (1132, '2020-04-13', 9.17, 4.79, 21.09, 5);
INSERT INTO `tb_consume` VALUES (1133, '2020-04-14', 1.86, 0.97, 20.12, 5);
INSERT INTO `tb_consume` VALUES (1134, '2020-04-15', 8.92, 4.66, 15.46, 5);
INSERT INTO `tb_consume` VALUES (1135, '2020-04-16', 4.60, 2.40, 13.06, 5);
INSERT INTO `tb_consume` VALUES (1136, '2020-04-17', 4.96, 2.59, 10.47, 5);
INSERT INTO `tb_consume` VALUES (1137, '2020-04-18', 8.27, 4.32, 6.15, 5);
INSERT INTO `tb_consume` VALUES (1138, '2020-04-19', 3.22, 1.68, 4.47, 5);
INSERT INTO `tb_consume` VALUES (1139, '2020-04-20', 2.09, 1.09, 3.38, 5);
INSERT INTO `tb_consume` VALUES (1140, '2020-04-21', 0.36, 0.19, 3.19, 5);
INSERT INTO `tb_consume` VALUES (1141, '2020-04-22', 2.04, 1.07, 2.12, 5);
INSERT INTO `tb_consume` VALUES (1142, '2020-04-23', 0.51, 0.27, 1.85, 5);
INSERT INTO `tb_consume` VALUES (1143, '2020-04-24', 5.51, 2.88, -1.03, 5);
INSERT INTO `tb_consume` VALUES (1144, '2020-04-01', 0.93, 0.49, 62.31, 4);
INSERT INTO `tb_consume` VALUES (1145, '2020-04-02', 5.82, 3.04, 59.27, 4);
INSERT INTO `tb_consume` VALUES (1146, '2020-04-03', 9.26, 4.84, 54.43, 4);
INSERT INTO `tb_consume` VALUES (1147, '2020-04-04', 3.42, 1.79, 52.64, 4);
INSERT INTO `tb_consume` VALUES (1148, '2020-04-05', 1.45, 0.76, 51.88, 4);
INSERT INTO `tb_consume` VALUES (1149, '2020-04-06', 3.99, 2.08, 49.80, 4);
INSERT INTO `tb_consume` VALUES (1150, '2020-04-07', 9.60, 5.02, 44.78, 4);
INSERT INTO `tb_consume` VALUES (1151, '2020-04-08', 10.00, 5.22, 39.56, 4);
INSERT INTO `tb_consume` VALUES (1152, '2020-04-09', 4.35, 2.27, 37.29, 4);
INSERT INTO `tb_consume` VALUES (1153, '2020-04-10', 0.55, 0.29, 37.00, 4);
INSERT INTO `tb_consume` VALUES (1154, '2020-04-11', 8.69, 4.54, 32.46, 4);
INSERT INTO `tb_consume` VALUES (1155, '2020-04-12', 4.99, 2.61, 29.85, 4);
INSERT INTO `tb_consume` VALUES (1156, '2020-04-13', 3.13, 1.64, 28.21, 4);
INSERT INTO `tb_consume` VALUES (1157, '2020-04-14', 0.56, 0.29, 27.92, 4);
INSERT INTO `tb_consume` VALUES (1158, '2020-04-15', 7.11, 3.71, 24.21, 4);
INSERT INTO `tb_consume` VALUES (1159, '2020-04-16', 1.36, 0.71, 23.50, 4);
INSERT INTO `tb_consume` VALUES (1160, '2020-04-17', 1.57, 0.82, 22.68, 4);
INSERT INTO `tb_consume` VALUES (1161, '2020-04-18', 3.08, 1.61, 21.07, 4);
INSERT INTO `tb_consume` VALUES (1162, '2020-04-19', 5.39, 2.82, 18.25, 4);
INSERT INTO `tb_consume` VALUES (1163, '2020-04-20', 2.25, 1.18, 17.07, 4);
INSERT INTO `tb_consume` VALUES (1164, '2020-04-21', 5.06, 2.64, 14.43, 4);
INSERT INTO `tb_consume` VALUES (1165, '2020-04-22', 6.99, 3.65, 10.78, 4);
INSERT INTO `tb_consume` VALUES (1166, '2020-04-23', 0.88, 0.46, 10.32, 4);
INSERT INTO `tb_consume` VALUES (1167, '2020-04-24', 0.41, 0.21, 10.11, 4);
INSERT INTO `tb_consume` VALUES (1168, '2020-04-25', 3.78, 1.97, 8.14, 4);
INSERT INTO `tb_consume` VALUES (1169, '2020-04-26', 4.16, 2.17, 5.97, 4);
INSERT INTO `tb_consume` VALUES (1170, '2020-04-27', 5.99, 3.13, 2.84, 4);
INSERT INTO `tb_consume` VALUES (1171, '2020-04-28', 6.58, 3.44, -0.60, 4);
INSERT INTO `tb_consume` VALUES (1172, '2020-04-01', 1.80, 0.94, 49.35, 3);
INSERT INTO `tb_consume` VALUES (1173, '2020-04-02', 4.67, 2.44, 46.91, 3);
INSERT INTO `tb_consume` VALUES (1174, '2020-04-03', 0.47, 0.25, 46.66, 3);
INSERT INTO `tb_consume` VALUES (1175, '2020-04-04', 4.42, 2.31, 44.35, 3);
INSERT INTO `tb_consume` VALUES (1176, '2020-04-05', 0.81, 0.42, 43.93, 3);
INSERT INTO `tb_consume` VALUES (1177, '2020-04-06', 9.24, 4.83, 39.10, 3);
INSERT INTO `tb_consume` VALUES (1178, '2020-04-07', 0.23, 0.12, 38.98, 3);
INSERT INTO `tb_consume` VALUES (1179, '2020-04-08', 7.70, 4.02, 34.96, 3);
INSERT INTO `tb_consume` VALUES (1180, '2020-04-09', 2.07, 1.08, 33.88, 3);
INSERT INTO `tb_consume` VALUES (1181, '2020-04-10', 6.49, 3.39, 30.49, 3);
INSERT INTO `tb_consume` VALUES (1182, '2020-04-11', 4.43, 2.31, 28.18, 3);
INSERT INTO `tb_consume` VALUES (1183, '2020-04-12', 2.24, 1.17, 27.01, 3);
INSERT INTO `tb_consume` VALUES (1184, '2020-04-13', 2.00, 1.04, 25.97, 3);
INSERT INTO `tb_consume` VALUES (1185, '2020-04-14', 3.91, 2.04, 23.93, 3);
INSERT INTO `tb_consume` VALUES (1186, '2020-04-15', 6.90, 3.60, 20.33, 3);
INSERT INTO `tb_consume` VALUES (1187, '2020-04-16', 9.00, 4.70, 15.63, 3);
INSERT INTO `tb_consume` VALUES (1188, '2020-04-17', 8.99, 4.70, 10.93, 3);
INSERT INTO `tb_consume` VALUES (1189, '2020-04-18', 2.66, 1.39, 9.54, 3);
INSERT INTO `tb_consume` VALUES (1190, '2020-04-19', 5.82, 3.04, 6.50, 3);
INSERT INTO `tb_consume` VALUES (1191, '2020-04-20', 3.87, 2.02, 4.48, 3);
INSERT INTO `tb_consume` VALUES (1192, '2020-04-21', 0.52, 0.27, 4.21, 3);
INSERT INTO `tb_consume` VALUES (1193, '2020-04-22', 4.30, 2.25, 1.96, 3);
INSERT INTO `tb_consume` VALUES (1194, '2020-04-23', 1.74, 0.91, 1.05, 3);
INSERT INTO `tb_consume` VALUES (1195, '2020-04-24', 8.07, 4.22, -3.17, 3);
INSERT INTO `tb_consume` VALUES (1196, '2020-04-01', 5.38, 2.81, 57.85, 2);
INSERT INTO `tb_consume` VALUES (1197, '2020-04-02', 6.35, 3.32, 54.53, 2);
INSERT INTO `tb_consume` VALUES (1198, '2020-04-03', 2.70, 1.41, 53.12, 2);
INSERT INTO `tb_consume` VALUES (1199, '2020-04-04', 2.78, 1.45, 51.67, 2);
INSERT INTO `tb_consume` VALUES (1200, '2020-04-05', 1.11, 0.58, 51.09, 2);
INSERT INTO `tb_consume` VALUES (1201, '2020-04-06', 6.53, 3.41, 47.68, 2);
INSERT INTO `tb_consume` VALUES (1202, '2020-04-07', 0.66, 0.34, 47.34, 2);
INSERT INTO `tb_consume` VALUES (1203, '2020-04-08', 6.23, 3.25, 44.09, 2);
INSERT INTO `tb_consume` VALUES (1204, '2020-04-09', 6.21, 3.24, 40.85, 2);
INSERT INTO `tb_consume` VALUES (1205, '2020-04-10', 0.61, 0.32, 40.53, 2);
INSERT INTO `tb_consume` VALUES (1206, '2020-04-11', 1.09, 0.57, 39.96, 2);
INSERT INTO `tb_consume` VALUES (1207, '2020-04-12', 0.69, 0.36, 39.60, 2);
INSERT INTO `tb_consume` VALUES (1208, '2020-04-13', 5.96, 3.11, 36.49, 2);
INSERT INTO `tb_consume` VALUES (1209, '2020-04-14', 7.53, 3.93, 32.56, 2);
INSERT INTO `tb_consume` VALUES (1210, '2020-04-15', 2.41, 1.26, 31.30, 2);
INSERT INTO `tb_consume` VALUES (1211, '2020-04-16', 1.54, 0.80, 30.50, 2);
INSERT INTO `tb_consume` VALUES (1212, '2020-04-17', 8.02, 4.19, 26.31, 2);
INSERT INTO `tb_consume` VALUES (1213, '2020-04-18', 7.39, 3.86, 22.45, 2);
INSERT INTO `tb_consume` VALUES (1214, '2020-04-19', 7.05, 3.68, 18.77, 2);
INSERT INTO `tb_consume` VALUES (1215, '2020-04-20', 0.78, 0.41, 18.36, 2);
INSERT INTO `tb_consume` VALUES (1216, '2020-04-21', 4.96, 2.59, 15.77, 2);
INSERT INTO `tb_consume` VALUES (1217, '2020-04-22', 1.13, 0.59, 15.18, 2);
INSERT INTO `tb_consume` VALUES (1218, '2020-04-23', 7.92, 4.14, 11.04, 2);
INSERT INTO `tb_consume` VALUES (1219, '2020-04-24', 1.78, 0.93, 10.11, 2);
INSERT INTO `tb_consume` VALUES (1220, '2020-04-25', 6.81, 3.56, 6.55, 2);
INSERT INTO `tb_consume` VALUES (1221, '2020-04-26', 9.20, 4.81, 1.74, 2);
INSERT INTO `tb_consume` VALUES (1222, '2020-04-27', 3.53, 1.84, -0.10, 2);
INSERT INTO `tb_consume` VALUES (1223, '2020-04-01', 7.43, 3.88, 96.27, 1);
INSERT INTO `tb_consume` VALUES (1224, '2020-04-02', 6.71, 3.51, 92.76, 1);
INSERT INTO `tb_consume` VALUES (1225, '2020-04-03', 3.79, 1.98, 90.78, 1);
INSERT INTO `tb_consume` VALUES (1226, '2020-04-04', 1.54, 0.80, 89.98, 1);
INSERT INTO `tb_consume` VALUES (1227, '2020-04-05', 5.00, 2.61, 87.37, 1);
INSERT INTO `tb_consume` VALUES (1228, '2020-04-06', 7.93, 4.14, 83.23, 1);
INSERT INTO `tb_consume` VALUES (1229, '2020-04-07', 5.73, 2.99, 80.24, 1);
INSERT INTO `tb_consume` VALUES (1230, '2020-04-08', 8.47, 4.42, 75.82, 1);
INSERT INTO `tb_consume` VALUES (1231, '2020-04-09', 8.13, 4.25, 71.57, 1);
INSERT INTO `tb_consume` VALUES (1232, '2020-04-10', 2.41, 1.26, 70.31, 1);
INSERT INTO `tb_consume` VALUES (1233, '2020-04-11', 7.97, 4.16, 66.15, 1);
INSERT INTO `tb_consume` VALUES (1234, '2020-04-12', 3.53, 1.84, 64.31, 1);
INSERT INTO `tb_consume` VALUES (1235, '2020-04-13', 3.16, 1.65, 62.66, 1);
INSERT INTO `tb_consume` VALUES (1236, '2020-04-14', 5.35, 2.79, 59.87, 1);
INSERT INTO `tb_consume` VALUES (1237, '2020-04-15', 2.96, 1.55, 58.32, 1);
INSERT INTO `tb_consume` VALUES (1238, '2020-04-16', 9.02, 4.71, 53.61, 1);
INSERT INTO `tb_consume` VALUES (1239, '2020-04-17', 4.23, 2.21, 51.40, 1);
INSERT INTO `tb_consume` VALUES (1240, '2020-04-18', 3.77, 1.97, 49.43, 1);
INSERT INTO `tb_consume` VALUES (1241, '2020-04-19', 8.28, 4.33, 45.10, 1);
INSERT INTO `tb_consume` VALUES (1242, '2020-04-20', 8.66, 4.52, 40.58, 1);
INSERT INTO `tb_consume` VALUES (1243, '2020-04-21', 5.16, 2.70, 37.88, 1);
INSERT INTO `tb_consume` VALUES (1244, '2020-04-22', 2.46, 1.29, 36.59, 1);
INSERT INTO `tb_consume` VALUES (1245, '2020-04-23', 9.84, 5.14, 31.45, 1);
INSERT INTO `tb_consume` VALUES (1246, '2020-04-24', 8.17, 4.27, 27.18, 1);
INSERT INTO `tb_consume` VALUES (1247, '2020-04-25', 1.99, 1.04, 26.14, 1);
INSERT INTO `tb_consume` VALUES (1248, '2020-04-26', 3.17, 1.66, 24.48, 1);
INSERT INTO `tb_consume` VALUES (1249, '2020-04-27', 7.08, 3.70, 20.78, 1);
INSERT INTO `tb_consume` VALUES (1250, '2020-04-28', 1.03, 0.54, 20.24, 1);
INSERT INTO `tb_consume` VALUES (1251, '2020-04-29', 2.89, 1.51, 18.73, 1);
INSERT INTO `tb_consume` VALUES (1252, '2020-04-30', 0.37, 0.19, 18.54, 1);
INSERT INTO `tb_consume` VALUES (1253, '2020-04-28', 0.00, 0.00, -0.10, 2);
INSERT INTO `tb_consume` VALUES (1254, '2020-04-29', 0.00, 0.00, -0.10, 2);
INSERT INTO `tb_consume` VALUES (1255, '2020-04-30', 0.00, 0.00, -0.10, 2);
INSERT INTO `tb_consume` VALUES (1256, '2020-04-25', 0.00, 0.00, -3.17, 3);
INSERT INTO `tb_consume` VALUES (1257, '2020-04-26', 0.00, 0.00, -3.17, 3);
INSERT INTO `tb_consume` VALUES (1258, '2020-04-27', 0.00, 0.00, -3.17, 3);
INSERT INTO `tb_consume` VALUES (1259, '2020-04-28', 0.00, 0.00, -3.17, 3);
INSERT INTO `tb_consume` VALUES (1260, '2020-04-29', 0.00, 0.00, -3.17, 3);
INSERT INTO `tb_consume` VALUES (1261, '2020-04-30', 0.00, 0.00, -3.17, 3);
INSERT INTO `tb_consume` VALUES (1262, '2020-04-29', 0.00, 0.00, -0.60, 4);
INSERT INTO `tb_consume` VALUES (1263, '2020-04-30', 0.00, 0.00, -0.60, 4);
INSERT INTO `tb_consume` VALUES (1264, '2020-04-25', 0.00, 0.00, -1.03, 5);
INSERT INTO `tb_consume` VALUES (1265, '2020-04-26', 0.00, 0.00, -1.03, 5);
INSERT INTO `tb_consume` VALUES (1266, '2020-04-27', 0.00, 0.00, -1.03, 5);
INSERT INTO `tb_consume` VALUES (1267, '2020-04-28', 0.00, 0.00, -1.03, 5);
INSERT INTO `tb_consume` VALUES (1268, '2020-04-29', 0.00, 0.00, -1.03, 5);
INSERT INTO `tb_consume` VALUES (1269, '2020-04-30', 0.00, 0.00, -1.03, 5);

-- ----------------------------
-- Table structure for tb_dormitory
-- ----------------------------
DROP TABLE IF EXISTS `tb_dormitory`;
CREATE TABLE `tb_dormitory`  (
  `pk_dormitory_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '宿舍ID',
  `dormitory_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '宿舍号',
  `dormitory_balance` double(10, 2) NOT NULL COMMENT '电费余额',
  `dormitory_status` int(1) NOT NULL COMMENT '送电状态 0：断电 1：送电',
  PRIMARY KEY (`pk_dormitory_id`, `dormitory_no`) USING BTREE,
  INDEX `pk_dormitory_id`(`pk_dormitory_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dormitory
-- ----------------------------
INSERT INTO `tb_dormitory` VALUES (1, '1042015', 58.54, 1);
INSERT INTO `tb_dormitory` VALUES (2, '1042014', 9.90, 1);
INSERT INTO `tb_dormitory` VALUES (3, '1032013', -3.17, 1);
INSERT INTO `tb_dormitory` VALUES (4, '1032014', -0.60, 0);
INSERT INTO `tb_dormitory` VALUES (5, '1022010', -1.03, 0);
INSERT INTO `tb_dormitory` VALUES (11, '1022011', 6.38, 1);

-- ----------------------------
-- Table structure for tb_pay
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay`;
CREATE TABLE `tb_pay`  (
  `pk_pay_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '充值记录ID',
  `pay_date` datetime(0) NOT NULL COMMENT '充值日期',
  `pay_amount` double(10, 2) NOT NULL COMMENT '充值金额',
  `pay_manner` int(1) NOT NULL COMMENT '充值方式 0：管理员充值 1：用户充值',
  `pay_pid` int(11) NOT NULL COMMENT '充值人ID',
  `dormitory_id` int(11) NOT NULL COMMENT '宿舍ID，外键',
  PRIMARY KEY (`pk_pay_id`) USING BTREE,
  INDEX `dormitory_id`(`dormitory_id`) USING BTREE,
  CONSTRAINT `tb_pay_ibfk_1` FOREIGN KEY (`dormitory_id`) REFERENCES `tb_dormitory` (`pk_dormitory_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_pay
-- ----------------------------
INSERT INTO `tb_pay` VALUES (1, '2019-10-16 10:20:00', 100.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (2, '2019-10-17 15:20:00', 200.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (3, '2019-10-18 14:13:56', 100.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (4, '2019-10-19 09:03:09', 1.00, 1, 8, 1);
INSERT INTO `tb_pay` VALUES (5, '2019-10-20 16:32:02', 100.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (6, '2019-10-21 16:32:30', 200.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (7, '2019-11-27 16:43:38', 1.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (8, '2019-12-05 15:29:08', 2.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (9, '2019-12-09 10:06:15', 10.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (10, '2019-12-09 10:41:48', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (11, '2019-12-09 10:42:16', 20.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (12, '2019-12-09 10:44:53', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (13, '2019-12-09 11:33:14', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (14, '2019-12-25 16:47:04', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (15, '2019-12-25 16:56:03', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (16, '2019-12-25 17:14:40', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (17, '2019-12-25 17:25:48', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (18, '2019-12-25 17:27:32', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (19, '2019-12-25 17:29:05', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (20, '2019-12-25 17:35:35', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (21, '2019-12-25 17:36:14', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (22, '2019-12-25 17:36:23', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (23, '2019-12-25 17:36:29', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (24, '2019-12-25 17:36:46', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (25, '2019-12-25 17:36:49', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (26, '2019-12-25 17:38:26', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (27, '2019-12-25 17:38:44', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (28, '2019-12-25 17:38:46', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (29, '2019-12-25 17:38:47', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (30, '2019-12-25 17:38:47', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (31, '2019-12-25 17:38:47', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (32, '2019-12-25 17:38:48', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (33, '2019-12-25 17:45:01', 20.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (34, '2019-12-25 18:07:10', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (35, '2020-01-06 23:05:37', 10.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (36, '2020-01-08 22:04:03', 10.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (37, '2020-01-11 17:52:35', 10.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (38, '2020-01-11 17:53:57', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (39, '2020-01-14 14:46:43', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (40, '2020-01-14 21:12:40', 20.00, 1, 1, 2);
INSERT INTO `tb_pay` VALUES (41, '2020-01-14 21:15:40', 20.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (42, '2020-01-14 21:19:20', 10.00, 1, 1, 2);
INSERT INTO `tb_pay` VALUES (43, '2020-01-15 15:58:47', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (44, '2020-01-19 15:47:53', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (45, '2020-01-19 15:54:08', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (46, '2020-01-19 16:09:16', 10.00, 1, 2, 1);
INSERT INTO `tb_pay` VALUES (47, '2020-02-17 13:56:03', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (48, '2020-02-17 14:02:57', 10.00, 0, 1, 2);
INSERT INTO `tb_pay` VALUES (49, '2020-03-16 03:44:54', 100.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (50, '2020-03-16 03:53:27', 100.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (51, '2020-03-27 19:34:26', 10.00, 0, 1, 1);
INSERT INTO `tb_pay` VALUES (52, '2020-03-28 16:55:47', 20.00, 1, 1, 1);
INSERT INTO `tb_pay` VALUES (53, '2020-03-28 17:14:03', 10.00, 1, 1, 1);
INSERT INTO `tb_pay` VALUES (54, '2020-05-02 20:04:55', 10.00, 0, 3, 1);
INSERT INTO `tb_pay` VALUES (58, '2020-05-07 15:14:33', 20.00, 1, 1, 1);
INSERT INTO `tb_pay` VALUES (59, '2020-05-07 15:23:27', 10.00, 1, 1, 1);
INSERT INTO `tb_pay` VALUES (60, '2020-05-10 13:27:46', 5.00, 1, 1, 11);
INSERT INTO `tb_pay` VALUES (61, '2020-05-10 14:25:35', 10.00, 1, 1, 2);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `pk_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名（学号）',
  `user_password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `user_real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `user_status` int(1) NOT NULL COMMENT '用户状态 0：禁用 1：启用',
  `dormitory_id` int(11) NULL DEFAULT NULL COMMENT '宿舍ID，外键',
  PRIMARY KEY (`pk_user_id`, `user_name`) USING BTREE,
  INDEX `dormitory_id`(`dormitory_id`) USING BTREE,
  CONSTRAINT `tb_user_ibfk_1` FOREIGN KEY (`dormitory_id`) REFERENCES `tb_dormitory` (`pk_dormitory_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '20164206001', '12345678', '刘一', '18212345678', 1, 1);
INSERT INTO `tb_user` VALUES (2, '20164206002', '12345678', '陈二', '18212345672', 1, 2);
INSERT INTO `tb_user` VALUES (7, '20164206003', '12345678', '张三', '18212345673', 1, 3);
INSERT INTO `tb_user` VALUES (8, '20174206001', '12345678', '李四', '18212345674', 0, 4);
INSERT INTO `tb_user` VALUES (9, '20174206002', '12345678', '王五', '18212345675', 0, 5);
INSERT INTO `tb_user` VALUES (10, '20174206003', '12345678', '赵六', '18212345676', 1, NULL);
INSERT INTO `tb_user` VALUES (11, '20184206001', '12345678', '孙七', '18212345677', 1, NULL);
INSERT INTO `tb_user` VALUES (12, '20184206002', '12345678', '周八', '18212345678', 1, NULL);
INSERT INTO `tb_user` VALUES (13, '20184206003', '12345678', '吴九', '18212345679', 1, NULL);
INSERT INTO `tb_user` VALUES (14, '201942061001', '12345678', '郑十', '18212345610', 1, NULL);
INSERT INTO `tb_user` VALUES (15, '20194206002', '12345678', '小明', '18212345611', 1, NULL);
INSERT INTO `tb_user` VALUES (16, '20194206003', '12345678', '小红', '18212345612', 1, NULL);
INSERT INTO `tb_user` VALUES (17, '20204206001', '11111111', '小强', '18212345678', 1, NULL);
INSERT INTO `tb_user` VALUES (18, '20204206002', '12345678', '小刚', '18212345678', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
