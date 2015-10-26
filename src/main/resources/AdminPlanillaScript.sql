-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.5.33a-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para adminplanillas
CREATE DATABASE IF NOT EXISTS `adminplanillas` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `adminplanillas`;


-- Volcando estructura para tabla adminplanillas.actividades
CREATE TABLE IF NOT EXISTS `actividades` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Fecha` datetime NOT NULL,
  `Status` bit(1) NOT NULL,
  `Id_Labor` int(10) NOT NULL,
  `Id_Empleado` int(10) NOT NULL,
  `Inactive_Date` datetime,
  PRIMARY KEY (`Id`),
  KEY `Labor_Id` (`Id_Labor`),
  KEY `Id_Empleado` (`Id_Empleado`),
  CONSTRAINT `Id_Empleado` FOREIGN KEY (`Id_Empleado`) REFERENCES `empleados` (`Id`),
  CONSTRAINT `Labor_Id` FOREIGN KEY (`Id_Labor`) REFERENCES `labores` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.

CREATE TABLE IF NOT EXISTS `planillas` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Fecha_Inicio` datetime NOT NULL,
  `Fecha_Fin` datetime NOT NULL,
  `Planilla_Creada` bit(1) NOT NULL,
  `Id_Deduccion` int(10) NOT NULL,
  `Inactive_Date` datetime ,
  PRIMARY KEY (`Id`),
  KEY `Id_Deduccion` (`Id_Deduccion`),
  CONSTRAINT `Id_Deduccion` FOREIGN KEY (`Id_Deduccion`) REFERENCES `deducciones` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Volcando estructura para tabla adminplanillas.actividades_x_planilla
CREATE TABLE IF NOT EXISTS `actividades_x_planilla` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Id_Actividad` int(10) NOT NULL,
  `Id_Planilla` int(10) NOT NULL,
  `Inactive_Date` datetime,
  UNIQUE KEY (`Id`),
  PRIMARY KEY (`Id_Actividad`,`Id_Planilla`),
  KEY `Planilla_Id` (`Id_Planilla`),
  CONSTRAINT `Actividad_Id` FOREIGN KEY (`Id_Actividad`) REFERENCES `actividades` (`Id`),
  CONSTRAINT `Planilla_Id` FOREIGN KEY (`Id_Planilla`) REFERENCES `planillas` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla adminplanillas.deducciones
CREATE TABLE IF NOT EXISTS `deducciones` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(50) NOT NULL,
  `Porcentaje` float NOT NULL,
  `Inactive_Date` datetime,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla adminplanillas.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `Apellido` varchar(50) NOT NULL,
  `Fecha_Nac` datetime NOT NULL,
  `Inactive_Date` datetime,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla adminplanillas.labores
CREATE TABLE IF NOT EXISTS `labores` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `Precio` float NOT NULL,
  `Id_Unidad` int(10) NOT NULL,
  `Inactive_Date` datetime,
  PRIMARY KEY (`Id`),
  KEY `Id_Unidad` (`Id_Unidad`),
  CONSTRAINT `Id_Unidad` FOREIGN KEY (`Id_Unidad`) REFERENCES `unidades` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla adminplanillas.planillas

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla adminplanillas.unidades
CREATE TABLE IF NOT EXISTS `unidades` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(50) NOT NULL,
  `Inactive_Date` datetime,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
