DROP TABLE Camilla IF EXISTS;
DROP TABLE Habitacion IF EXISTS;
DROP TABLE Historial IF EXISTS;
DROP TABLE Piso IF EXISTS;

CREATE TABLE Camilla (
  id         		INTEGER IDENTITY PRIMARY KEY,
  tipo	 		VARCHAR(40),
  estado			VARCHAR(40),
  anio INTEGER
);

CREATE TABLE Habitacion (
                         id         		INTEGER IDENTITY PRIMARY KEY,
                         especialidad	 		VARCHAR(40),
                         estado			VARCHAR(40),
                         nro_camas_max INTEGER
);

CREATE TABLE Hisotrial (
                         id         		INTEGER IDENTITY PRIMARY KEY,
                         fecha_ingreso	 		DATE,
                         fecha_salida			DATE,
);

CREATE TABLE Piso (
                         id         		INTEGER IDENTITY PRIMARY KEY,
                         nombre	 		VARCHAR(40),
                         estado			VARCHAR(40),
                         nro_habitaciones INTEGER
);