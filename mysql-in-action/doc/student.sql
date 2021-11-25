SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'R1', 'A');
INSERT INTO `student` VALUES (2, 'R1', 'B');
INSERT INTO `student` VALUES (3, 'R2', 'C');
INSERT INTO `student` VALUES (4, 'R1', 'D');
INSERT INTO `student` VALUES (5, 'R3', 'E');
INSERT INTO `student` VALUES (6, 'R4', 'F');
INSERT INTO `student` VALUES (7, 'R3', 'G');
INSERT INTO `student` VALUES (8, 'R5', 'H');
INSERT INTO `student` VALUES (9, 'R4', 'I');
INSERT INTO `student` VALUES (10, 'R6', 'J');

SET FOREIGN_KEY_CHECKS = 1;