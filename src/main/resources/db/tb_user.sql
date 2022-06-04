/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql-dev
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 192.168.100.100:3306
 Source Schema         : ssm-demo

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 03/06/2022 23:29:22
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`
(
    `id`       bigint                                                  NOT NULL,
    `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `password` varbinary(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
