/*
 Navicat Premium Data Transfer

 Source Server         : vm
 Source Server Type    : MySQL
 Source Server Version : 80024 (8.0.24)
 Source Host           : 10.0.0.2:3306
 Source Schema         : netdrive

 Target Server Type    : MySQL
 Target Server Version : 80024 (8.0.24)
 File Encoding         : 65001

 Date: 17/07/2023 20:21:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `createTime` datetime NOT NULL,
  `isShared` tinyint NOT NULL,
  `ownerId` int NOT NULL,
  `filesize` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `count` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (95, 'photo_2023-03-20_15-26-16.jpg', 'D:\\test\\NetDrive\\Files\\admin\\photo_2023-03-20_15-26-16.jpg', '2023-07-17 11:56:58', 1, 10, '109.71KB', 4);
INSERT INTO `file` VALUES (96, '#博衣こより Koyori hololive - Nguyên Napie.jpg', 'D:\\test\\NetDrive\\Files\\admin\\#博衣こより Koyori hololive - Nguyên Napie.jpg', '2023-07-17 11:57:46', 0, 10, '684.27KB', 0);
INSERT INTO `file` VALUES (97, '#樂兔workshop Hololive production.png', 'D:\\test\\NetDrive\\Files\\admin\\#樂兔workshop Hololive production.png', '2023-07-17 11:57:58', 0, 10, '1.15MB', 0);
INSERT INTO `file` VALUES (98, '同步空间新手教程.docx', 'D:\\test\\NetDrive\\Files\\xiaoming\\同步空间新手教程.docx', '2023-07-17 12:01:37', 1, 11, '182.57KB', 1);
INSERT INTO `file` VALUES (99, 'OfficeSetup.exe', 'D:\\test\\NetDrive\\Files\\xiaoming\\OfficeSetup.exe', '2023-07-17 12:04:03', 1, 11, '7.17MB', 0);
INSERT INTO `file` VALUES (100, '泠鸢yousa,音阙诗听 - 大喜.flac', 'D:\\test\\NetDrive\\Files\\xiaoming\\泠鸢yousa,音阙诗听 - 大喜.flac', '2023-07-17 12:06:25', 1, 11, '37.70MB', 0);
INSERT INTO `file` VALUES (101, '泠鸢yousa - 序曲·折纸信笺.flac', 'D:\\test\\NetDrive\\Files\\xiaoming\\泠鸢yousa - 序曲·折纸信笺.flac', '2023-07-17 12:08:12', 1, 11, '10.83MB', 0);
INSERT INTO `file` VALUES (102, '可颂猫.mp4', 'D:\\test\\NetDrive\\Files\\tom\\可颂猫.mp4', '2023-07-17 12:10:44', 0, 12, '664.13KB', 0);
INSERT INTO `file` VALUES (103, '老 表 猫.mp4', 'D:\\test\\NetDrive\\Files\\tom\\老 表 猫.mp4', '2023-07-17 12:11:03', 0, 12, '2.91MB', 0);
INSERT INTO `file` VALUES (104, '卢本伟_玩游戏一定要笑着玩.mp4', 'D:\\test\\NetDrive\\Files\\tom\\卢本伟_玩游戏一定要笑着玩.mp4', '2023-07-17 12:16:44', 0, 12, '2.22MB', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `isAdmin` int(11) UNSIGNED ZEROFILL NOT NULL DEFAULT 00000000000,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10, 'admin', 'ww112233', 00000000001);
INSERT INTO `user` VALUES (11, 'xiaoming', '111111', 00000000000);
INSERT INTO `user` VALUES (12, 'tom', '111111', 00000000000);

SET FOREIGN_KEY_CHECKS = 1;
