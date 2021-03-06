USE [master]
GO
/****** Object:  Database [casptonetrafficjamdb]    Script Date: 4/3/2019 10:23:50 AM ******/
CREATE DATABASE [casptonetrafficjamdb]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'casptonetrafficjamdb', FILENAME = N'P:\Microsoft SQL\Data\casptonetrafficjamdb.mdf' , SIZE = 3264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'casptonetrafficjamdb_log', FILENAME = N'P:\Microsoft SQL\Data\casptonetrafficjamdb_log.ldf' , SIZE = 1088KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [casptonetrafficjamdb] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [casptonetrafficjamdb].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [casptonetrafficjamdb] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET ARITHABORT OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [casptonetrafficjamdb] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [casptonetrafficjamdb] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET  DISABLE_BROKER 
GO
ALTER DATABASE [casptonetrafficjamdb] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET ALLOW_SNAPSHOT_ISOLATION ON 
GO
ALTER DATABASE [casptonetrafficjamdb] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [casptonetrafficjamdb] SET READ_COMMITTED_SNAPSHOT ON 
GO
ALTER DATABASE [casptonetrafficjamdb] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET RECOVERY FULL 
GO
ALTER DATABASE [casptonetrafficjamdb] SET  MULTI_USER 
GO
ALTER DATABASE [casptonetrafficjamdb] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [casptonetrafficjamdb] SET DB_CHAINING OFF 
GO
ALTER DATABASE [casptonetrafficjamdb] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [casptonetrafficjamdb] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [casptonetrafficjamdb] SET DELAYED_DURABILITY = DISABLED 
GO
USE [casptonetrafficjamdb]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 4/3/2019 10:23:50 AM ******/
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
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Bookmark]    Script Date: 4/3/2019 10:23:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bookmark](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[account_id] [int] NULL,
	[origin] [nvarchar](max) NULL,
	[destination] [nvarchar](max) NULL,
	[ori_coordinate] [nvarchar](max) NULL,
	[des_coordinate] [nvarchar](max) NULL,
 CONSTRAINT [PK_Bookmark] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Bookmark_Camera]    Script Date: 4/3/2019 10:23:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bookmark_Camera](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[camera_id] [int] NOT NULL,
	[bookmark_id] [int] NOT NULL,
 CONSTRAINT [PK_Camera_User] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Camera]    Script Date: 4/3/2019 10:23:50 AM ******/
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
	[street_id] [int] NULL,
	[isActive] [bit] NULL,
	[image_url] [nvarchar](255) NULL,
	[time] [datetime] NULL,
 CONSTRAINT [PK_Camera] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Role]    Script Date: 4/3/2019 10:23:50 AM ******/
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
/****** Object:  Table [dbo].[Street]    Script Date: 4/3/2019 10:23:50 AM ******/
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
SET IDENTITY_INSERT [dbo].[Account] ON 

INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (1, N'admin', N'123', N'admin', 1)
INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (2, N'admin1', N'123', N'admin1', 1)
INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (3, N'admin3', N'123', N'admin3', 2)
INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (1004, N'ssss', N'aaaa', N'ssss', 2)
INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (1005, N'qqq', N'www', N'aaaa', 2)
INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (1006, N'asdewq', N'a', N'a', 2)
INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (1007, N'hieptb', N'123456', N'Hiep', 2)
INSERT [dbo].[Account] ([id], [username], [password], [name], [role_id]) VALUES (1008, N'dungbm', N'123', N'Dung', 2)
SET IDENTITY_INSERT [dbo].[Account] OFF
SET IDENTITY_INSERT [dbo].[Bookmark] ON 

INSERT [dbo].[Bookmark] ([id], [account_id], [origin], [destination], [ori_coordinate], [des_coordinate]) VALUES (3, 1008, N'Quang Trung', N'Nguyễn Kiệm', N'1', N'1')
INSERT [dbo].[Bookmark] ([id], [account_id], [origin], [destination], [ori_coordinate], [des_coordinate]) VALUES (4, 1008, N'Quang Trung', N'Cong Hoa', N'1', N'1')
INSERT [dbo].[Bookmark] ([id], [account_id], [origin], [destination], [ori_coordinate], [des_coordinate]) VALUES (6, 1007, N'to ky quan 12', N'nguyen kiem go vap', N'10.8667,106.6157', N'10.81823,106.67876')
SET IDENTITY_INSERT [dbo].[Bookmark] OFF
SET IDENTITY_INSERT [dbo].[Bookmark_Camera] ON 

INSERT [dbo].[Bookmark_Camera] ([id], [camera_id], [bookmark_id]) VALUES (4, 2, 4)
INSERT [dbo].[Bookmark_Camera] ([id], [camera_id], [bookmark_id]) VALUES (6, 2, 4)
INSERT [dbo].[Bookmark_Camera] ([id], [camera_id], [bookmark_id]) VALUES (7, 1, 6)
INSERT [dbo].[Bookmark_Camera] ([id], [camera_id], [bookmark_id]) VALUES (8, 3, 6)
SET IDENTITY_INSERT [dbo].[Bookmark_Camera] OFF
SET IDENTITY_INSERT [dbo].[Camera] ON 

INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (1, N'Siêu thị Văn Lang', N'10.826863, 106.678745', NULL, 0, 1, 1, 1, N'gg.com', CAST(N'2019-03-24 06:07:00.000' AS DateTime))
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (2, N'Chùa Huỳnh Kim', N'10.835282, 106.663070', NULL, 0, 2, 1, 1, NULL, CAST(N'2019-03-24 06:07:00.000' AS DateTime))
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (3, N'Ngã Ba Tân Sơn', N'10.839334, 106.647175', NULL, 0, 3, 1, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (4, N'Chợ Cầu', N'10.846166, 106.637259', N'111', 1, 4, 1, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (5, N'CVPM Quang Trung', N'10.852615, 106.626573', N'111', 1, 1, 4, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (6, N'Bệnh Viện 175', N'10.817964, 106.681598', NULL, 1, 1, 3, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (8, N'ĐH Công Nghiệp', N'10.822808, 106.685417', NULL, 0, 2, 3, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (10, N'Cư xá Nam Sơn', N'10.836035, 106.675584', NULL, 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (11, N'CV Mũi Tàu', N'10.830669, 106.677705', NULL, 0, 2, 2, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (12, N'Cầu vượt ngã sáu Gò Vấp', N'10.828573, 106.678951', NULL, 1, 3, 2, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (13, N'Nhà Sách Phan Huy Ích', N'10.837825, 106.636491', NULL, 2, 1, 25, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (14, N'Vịt nướng Tư Phương', N'10.840529, 106.637724', NULL, 0, 2, 25, 1, NULL, NULL)
INSERT [dbo].[Camera] ([id], [description], [position], [resource], [observed_status], [camOrder], [street_id], [isActive], [image_url], [time]) VALUES (15, N'COOP FOOD', N'10.841948, 106.638635', NULL, 1, 3, 25, 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Camera] OFF
SET IDENTITY_INSERT [dbo].[Role] ON 

INSERT [dbo].[Role] ([id], [role]) VALUES (1, N'admin')
INSERT [dbo].[Role] ([id], [role]) VALUES (2, N'user')
SET IDENTITY_INSERT [dbo].[Role] OFF
SET IDENTITY_INSERT [dbo].[Street] ON 

INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (1, N'Quang Trung', N'Gò Vấp', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (2, N'Nguyễn Oanh', N'Gò Vấp', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (3, N'Nguyễn Thái Sơn', N'Gò Vấp', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (4, N'Tô Ký', N'Gò Vấp', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (5, N'Trường Chinh', N'Tân Bình', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (6, N'CMT8', N'Tân Bình', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (7, N'Trường Chinh', N'Tân Bình', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (8, N'Sư Vạn Hạnh', N'10', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (9, N'Lý Thường Kiẹt', N'Tân Bình', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (10, N'Lê Hồng Phong', N'3', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (11, N'Trần Hưng Đạo', N'1', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (12, N'Pasteur', N'1', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (13, N'Võ Thi Sáu', N'1', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (14, N'Nguyễn Thị Minh Khai', N'1', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (15, N'Nguyễn Chí Thanh', N'5', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (16, N'Lý Thái Tổ ', N'10', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (17, N'Thành Thái', N'10', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (18, N'Đồng Nai', N'10', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (19, N'Nguyễn Trãi', N'1', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (20, N'Ngô Quyền', N'2', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (21, N'Nguyễn Hiền', N'4', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (22, N'Ngô Gia Tự', N'5', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (23, N'Nam Kỳ Khởi Nghĩa', N'3', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (24, N'Nguyễn Văn Trỗi', N'9', N'TP Hồ Chí Minh', 1)
INSERT [dbo].[Street] ([id], [name], [district], [city], [isActive]) VALUES (25, N'Phan Huy Ích', N'Gò Vấp', N'TP Hồ Chí Minh', 1)
SET IDENTITY_INSERT [dbo].[Street] OFF
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
ALTER TABLE [dbo].[Bookmark_Camera]  WITH CHECK ADD  CONSTRAINT [FK_Bookmark_Camera_Bookmark] FOREIGN KEY([bookmark_id])
REFERENCES [dbo].[Bookmark] ([id])
GO
ALTER TABLE [dbo].[Bookmark_Camera] CHECK CONSTRAINT [FK_Bookmark_Camera_Bookmark]
GO
ALTER TABLE [dbo].[Bookmark_Camera]  WITH CHECK ADD  CONSTRAINT [FK_Camera_User_Camera] FOREIGN KEY([camera_id])
REFERENCES [dbo].[Camera] ([id])
GO
ALTER TABLE [dbo].[Bookmark_Camera] CHECK CONSTRAINT [FK_Camera_User_Camera]
GO
ALTER TABLE [dbo].[Camera]  WITH CHECK ADD  CONSTRAINT [FK_Camera_Street] FOREIGN KEY([street_id])
REFERENCES [dbo].[Street] ([id])
GO
ALTER TABLE [dbo].[Camera] CHECK CONSTRAINT [FK_Camera_Street]
GO
USE [master]
GO
ALTER DATABASE [casptonetrafficjamdb] SET  READ_WRITE 
GO
