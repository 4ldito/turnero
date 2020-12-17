-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3308
-- Tiempo de generación: 13-11-2020 a las 23:41:40
-- Versión del servidor: 8.0.18
-- Versión de PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_turnos`
--
DROP DATABASE IF EXISTS `sistema_turnos`;
CREATE DATABASE IF NOT EXISTS `sistema_turnos` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `sistema_turnos`;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `agenda`
--

DROP TABLE IF EXISTS `agenda`;
CREATE TABLE IF NOT EXISTS `agenda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `id_especialidad` int(11) NOT NULL,
  `id_medico` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_medico` (`id_medico`),
  KEY `id_especialidad` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `agenda`
--

INSERT INTO `agenda` (`id`, `nombre`, `id_especialidad`, `id_medico`, `activo`) VALUES
(3, 'Agenda Marinelli', 1, 5, b'1'),
(4, 'Agenda Fondo de Ojos', 1, 5, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
CREATE TABLE IF NOT EXISTS `especialidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `especialidad`
--

INSERT INTO `especialidad` (`id`, `nombre`, `activo`) VALUES
(1, 'Odontología', b'1'),
(2, 'Pediatría', b'1'),
(3, 'Kinesiología', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_turno`
--

DROP TABLE IF EXISTS `estado_turno`;
CREATE TABLE IF NOT EXISTS `estado_turno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `estado_turno`
--

INSERT INTO `estado_turno` (`id`, `nombre`) VALUES
(1, 'Atendido'),
(2, 'Citado'),
(5, 'Cancelado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero`
--

DROP TABLE IF EXISTS `genero`;
CREATE TABLE IF NOT EXISTS `genero` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sexo` varchar(12) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `genero`
--

INSERT INTO `genero` (`id`, `sexo`) VALUES
(1, 'Masculino'),
(2, 'Femenino'),
(3, 'Otro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horario`
--

DROP TABLE IF EXISTS `horario`;
CREATE TABLE IF NOT EXISTS `horario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_agenda` int(11) NOT NULL,
  `nombre` varchar(150) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `hs_inicio` time NOT NULL,
  `hs_fin` time NOT NULL,
  `intervalo` int(11) NOT NULL,
  `lunes` tinyint(1) NOT NULL,
  `martes` tinyint(1) NOT NULL,
  `miercoles` tinyint(1) NOT NULL,
  `jueves` tinyint(1) NOT NULL,
  `viernes` tinyint(1) NOT NULL,
  `sabado` tinyint(1) NOT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_agenda` (`id_agenda`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `horario`
--

INSERT INTO `horario` (`id`, `id_agenda`, `nombre`, `hs_inicio`, `hs_fin`, `intervalo`, `lunes`, `martes`, `miercoles`, `jueves`, `viernes`, `sabado`, `activo`) VALUES
(1, 3, 'Lunes', '07:00:00', '10:00:00', 30, 1, 0, 0, 0, 0, 0, b'1'),
(2, 3, 'Miercoles y viernes', '14:00:00', '23:00:00', 20, 0, 0, 1, 0, 1, 0, b'1'),
(3, 4, 'Lunes', '17:00:00', '18:00:00', 20, 1, 0, 0, 0, 0, 0, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

DROP TABLE IF EXISTS `paciente`;
CREATE TABLE IF NOT EXISTS `paciente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dni` int(11) NOT NULL,
  `nombre` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `apellido` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `celular` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `id_genero` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`),
  KEY `id_sexo` (`id_genero`),
  KEY `dni_2` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`id`, `dni`, `nombre`, `apellido`, `direccion`, `celular`, `id_genero`, `activo`) VALUES
(1, 41975263, 'Gonzalo', 'Marinelli', 'Caseros 654', '3462699633', 1, b'1'),
(2, 41240398, 'Pablito', 'Caffa', '9 JULIO 1016', '34629999999', 2, b'1'),
(3, 41975266, 'Francisco', 'Germani', 'caseros 456', '654987321', 3, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(25) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `tipo`) VALUES
(1, 'Administrativo'),
(2, 'Médico'),
(3, 'Profesional de la salud');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turno`
--

DROP TABLE IF EXISTS `turno`;
CREATE TABLE IF NOT EXISTS `turno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_agenda` int(11) NOT NULL,
  `id_especialidad` int(11) NOT NULL,
  `id_horario` int(11) NOT NULL,
  `id_paciente` int(11) NOT NULL,
  `fecha_turno` date NOT NULL,
  `hs_inicio` time NOT NULL,
  `hs_fin` time NOT NULL,
  `num_slot` int(2) NOT NULL,
  `id_estado` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_med_esp` (`id_horario`,`id_paciente`),
  KEY `id_paciente` (`id_paciente`),
  KEY `id_medico` (`id_agenda`),
  KEY `id_estado` (`id_estado`),
  KEY `id_especialidad` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `turno`
--

INSERT INTO `turno` (`id`, `id_agenda`, `id_especialidad`, `id_horario`, `id_paciente`, `fecha_turno`, `hs_inicio`, `hs_fin`, `num_slot`, `id_estado`, `activo`) VALUES
(1, 3, 1, 1, 1, '2020-11-16', '07:00:00', '07:30:00', 1, 2, b'0'),
(2, 3, 1, 1, 1, '2020-11-16', '07:00:00', '07:30:00', 1, 2, b'0'),
(3, 3, 1, 1, 2, '2020-11-16', '07:00:00', '07:30:00', 1, 2, b'1'),
(4, 3, 1, 2, 1, '2020-11-13', '20:40:00', '21:00:00', 21, 1, b'1'),
(5, 3, 1, 2, 2, '2020-11-13', '21:40:00', '22:00:00', 24, 5, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rol` int(11) NOT NULL,
  `dni` int(8) NOT NULL,
  `nombre_usuario` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `clave` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `apellido` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `celular` int(40) NOT NULL,
  `id_genero` int(11) NOT NULL,
  `id_especialidad` int(11) DEFAULT NULL,
  `matricula` int(10) DEFAULT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_tipo_usuario` (`rol`),
  KEY `id_genero` (`id_genero`),
  KEY `id_especialidad` (`id_especialidad`),
  KEY `matricula` (`matricula`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `rol`, `dni`, `nombre_usuario`, `clave`, `nombre`, `apellido`, `celular`, `id_genero`, `id_especialidad`, `matricula`, `activo`) VALUES
(1, 1, 11111111, 'adm', 'adm', 'Administrativo', 'Administrativo', 55555555, 3, NULL, NULL, b'1'),
(3, 2, 22222222, 'medico', 'medico', 'medico', 'medico', 33333333, 3, 1, 2813, b'1'),
(4, 2, 123, 'gon', 'gon', 'gon', 'gon', 123, 1, 1, 123, b'0'),
(5, 2, 41975263, 'gon', 'gon', 'Gonzalo', 'Marinelli', 2147483647, 1, 1, 2233, b'1'),
(6, 1, 20202020, 'riki', 'riki', 'riki', 'Fort', 456, 1, NULL, NULL, b'1'),
(7, 1, 123, 'ASD', 'ASD', 'ASD', 'ASD', 123, 1, NULL, NULL, b'0'),
(8, 1, 123, 'asd', 'asd', 'asd', 'asd', 123, 1, NULL, NULL, b'0'),
(9, 1, 123123, 'asd', 'asd', 'asd', 'asds', 123, 1, NULL, NULL, b'0'),
(10, 1, 4111, 'asds', 'sss', 'sss', 'asd', 1234, 1, NULL, NULL, b'0');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `agenda`
--
ALTER TABLE `agenda`
  ADD CONSTRAINT `agenda_ibfk_1` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `agenda_ibfk_3` FOREIGN KEY (`id_medico`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `horario`
--
ALTER TABLE `horario`
  ADD CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`id_agenda`) REFERENCES `agenda` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `paciente_ibfk_1` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `turno`
--
ALTER TABLE `turno`
  ADD CONSTRAINT `turno_ibfk_1` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `turno_ibfk_4` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `turno_ibfk_5` FOREIGN KEY (`id_estado`) REFERENCES `estado_turno` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `turno_ibfk_6` FOREIGN KEY (`id_horario`) REFERENCES `horario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `turno_ibfk_7` FOREIGN KEY (`id_agenda`) REFERENCES `agenda` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `rol` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usuario_ibfk_3` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
