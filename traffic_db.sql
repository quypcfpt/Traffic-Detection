USE [master]
GO
/****** Object:  Database [traffic_db]    Script Date: 3/1/2019 10:31:27 PM ******/
CREATE DATABASE [traffic_db]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'traffic_db', FILENAME = N'P:\Microsoft SQL\Data\traffic_db.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'traffic_db_log', FILENAME = N'P:\Microsoft SQL\Data\traffic_db_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
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
USE [traffic_db]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 3/1/2019 10:31:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](255) NULL,
	[password] [nvarchar](255) NULL,
	[name] [nvarchar](255) NULL,
	[role_id] [int] NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Bookmark]    Script Date: 3/1/2019 10:31:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bookmark](
	[id] [int] NOT NULL,
	[account_id] [int] NULL,
	[route_json] [nvarchar](max) NULL,
 CONSTRAINT [PK_Bookmark] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Camera]    Script Date: 3/1/2019 10:31:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Camera](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[description] [nvarchar](255) NULL,
	[position] [nvarchar](255) NULL,
	[resource] [nvarchar](255) NULL,
	[observed_status] [int] NULL,
	[camOrder] [int] NULL,
	[width] [float] NULL,
	[street_id] [int] NULL,
	[isActive] [bit] NULL,
 CONSTRAINT [PK_Camera] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Image]    Script Date: 3/1/2019 10:31:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Image](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[camera_id] [int] NULL,
	[link] [nvarchar](255) NULL,
	[time] [datetime] NULL,
 CONSTRAINT [PK_Image] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Role]    Script Date: 3/1/2019 10:31:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role] [nvarchar](255) NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Street]    Script Date: 3/1/2019 10:31:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Street](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
	[district] [nvarchar](255) NULL,
	[city] [nvarchar](255) NULL,
	[isActive] [bit] NULL,
 CONSTRAINT [PK_Street] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_Role] FOREIGN KEY([role_id])
REFERENCES [dbo].[Role] ([id])
GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FK_Account_Role]
GO
ALTER TABLE [dbo].[Bookmark]  WITH CHECK ADD  CONSTRAINT [FK_Bookmark_Account] FOREIGN KEY([account_id])
REFERENCES [dbo].[Account] ([id])
GO
ALTER TABLE [dbo].[Bookmark] CHECK CONSTRAINT [FK_Bookmark_Account]
GO
ALTER TABLE [dbo].[Camera]  WITH CHECK ADD  CONSTRAINT [FK_Camera_Street] FOREIGN KEY([street_id])
REFERENCES [dbo].[Street] ([id])
GO
ALTER TABLE [dbo].[Camera] CHECK CONSTRAINT [FK_Camera_Street]
GO
ALTER TABLE [dbo].[Image]  WITH CHECK ADD  CONSTRAINT [FK_Image_Camera] FOREIGN KEY([camera_id])
REFERENCES [dbo].[Camera] ([id])
GO
ALTER TABLE [dbo].[Image] CHECK CONSTRAINT [FK_Image_Camera]
GO
USE [master]
GO
ALTER DATABASE [traffic_db] SET  READ_WRITE 
GO
