USE [master]
GO
/****** Object:  Database [traffic_db]    Script Date: 2/27/2019 3:26:49 PM ******/
CREATE DATABASE [traffic_db]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'traffic_db', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\traffic_db.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'traffic_db_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\traffic_db_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [traffic_db] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [traffic_db].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [traffic_db] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [traffic_db] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [traffic_db] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [traffic_db] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [traffic_db] SET ARITHABORT OFF 
GO
ALTER DATABASE [traffic_db] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [traffic_db] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [traffic_db] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [traffic_db] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [traffic_db] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [traffic_db] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [traffic_db] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [traffic_db] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [traffic_db] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [traffic_db] SET  DISABLE_BROKER 
GO
ALTER DATABASE [traffic_db] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [traffic_db] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [traffic_db] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [traffic_db] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [traffic_db] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [traffic_db] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [traffic_db] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [traffic_db] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [traffic_db] SET  MULTI_USER 
GO
ALTER DATABASE [traffic_db] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [traffic_db] SET DB_CHAINING OFF 
GO
ALTER DATABASE [traffic_db] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [traffic_db] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [traffic_db] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'traffic_db', N'ON'
GO
ALTER DATABASE [traffic_db] SET QUERY_STORE = OFF
GO
USE [traffic_db]
GO
ALTER DATABASE SCOPED CONFIGURATION SET IDENTITY_CACHE = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [traffic_db]
GO
/****** Object:  Table [dbo].[Camera]    Script Date: 2/27/2019 3:26:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Camera](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [nvarchar](25) NULL,
	[latitude] [float] NULL,
	[longtitude] [float] NULL,
	[camOrder] [int] NULL,
	[width] [int] NULL,
	[street_id] [int] NULL,
	[image] [nvarchar](150) NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_Camera] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Street]    Script Date: 2/27/2019 3:26:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Street](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
	[district] [nvarchar](255) NULL,
	[city] [nvarchar](50) NULL,
 CONSTRAINT [PK_Street] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Vehicle]    Script Date: 2/27/2019 3:26:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Vehicle](
	[id] [int] NOT NULL,
	[type] [nvarchar](255) NULL,
	[width] [int] NULL,
 CONSTRAINT [PK_Vehicle] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Camera] ON 

INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (1, N'QT01', 0, 0, 1, 600, 1, N'AAA', 1)
INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (2, N'QT02', 1, 1, 2, 700, 1, N'BBB', 0)
INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (3, N'QT03', 2, 2, 3, 800, 1, N'CCC', 0)
INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (4, N'CH01', 0, 0, 2, 600, 2, N'VVV', 0)
INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (5, N'CH02', 1, 1, 1, 700, 2, N'QQQ', 1)
INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (6, N'QT04', 1, 1, 4, 1, 1, N'SSS', 1)
INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (9, N'QT05', 1, 1, 5, 555, 1, N'TTT', 1)
INSERT [dbo].[Camera] ([id], [code], [latitude], [longtitude], [camOrder], [width], [street_id], [image], [status]) VALUES (10, N'QT06', 1, 1, 6, 777, 1, N'GGG', 0)
SET IDENTITY_INSERT [dbo].[Camera] OFF
SET IDENTITY_INSERT [dbo].[Street] ON 

INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (1, N'Quang Trung', N'Go Vap', N'HCM')
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (2, N'Cộng Hòa', N'Tan Binh', N'HCM')
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (3, N'Truong Trinh', N'Tan Binh', N'HCM')
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (4, N'Phan Huy Ich', N'Go Vap', N'HCM')
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (5, N'Hoang Van Thu', N'Tan Binh', N'HCM')
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (6, N'Phan Van Hon', N'Tan Binh', N'HCM')
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (7, N'CMT8', N'Tan Binh', N'HCM')
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (8, N'Tran Hung Dao', N'1', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (9, N'Vo thi Sau', N'3', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (10, N'Cao Thang', N'3', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (11, N'Tran Huy Lieu', N'3', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (12, N'Dong den', N'Tan Phu', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (13, N'Bau Cat', N'Tan Phu', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (14, N'Paster', N'1', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (15, N'Nam Ky Khoi Nghia', N'Tan Binh', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (16, N'Doc Lap', N'Tan Phu', NULL)
INSERT [dbo].[Street] ([id], [name], [district], [city]) VALUES (17, N'Nguyen Thai Son', N'Go Vap', NULL)
SET IDENTITY_INSERT [dbo].[Street] OFF
ALTER TABLE [dbo].[Camera]  WITH CHECK ADD  CONSTRAINT [FK_Camera_Street] FOREIGN KEY([street_id])
REFERENCES [dbo].[Street] ([id])
GO
ALTER TABLE [dbo].[Camera] CHECK CONSTRAINT [FK_Camera_Street]
GO
USE [master]
GO
ALTER DATABASE [traffic_db] SET  READ_WRITE 
GO
