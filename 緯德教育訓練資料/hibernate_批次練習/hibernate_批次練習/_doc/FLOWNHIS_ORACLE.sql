/*==============================================================*/
/* Table: FLOWNHIS                                              */
/*==============================================================*/
create table v_flownhis  (
   FIS_NBR              INTEGER                         not null,
   FIS_CARRIER          CHAR(2),
   FIS_FLIGHT           CHAR(4),
   FIS_SDATE            DATE,
   FIS_PORT_FD          CHAR(3),
   FIS_PASSNAME         NVARCHAR2(100),
   FIS_CABINC           CHAR(1),
   FIS_SALESC           CHAR(1),
   FIS_PORT_FA          CHAR(3),
   FIS_SECNBR           CHAR(3),
   FIS_STATUS           CHAR(1),
   FIS_ET               CHAR(13),
   FIS_FILLER           CHAR(16),
   FIS_COUPON           CHAR(1),
   FIS_FARETYPE         NVARCHAR2(40),
   FIS_BASIS            CHAR(15),
   FIS_CUSNO            VARCHAR(10),
   FIS_CTYPE            CHAR(2),
   FIS_FCARRIER         CHAR(3),
   FIS_MARKET           CHAR(7),
   FIS_PNR              CHAR(6),
   FIS_MARK             CHAR(4),
   FIS_FLIGHT_O         CHAR(7),
   FIS_DATE_O           CHAR(5),
   FIS_CITY_O           CHAR(3),
   FIS_CLASS_O          CHAR(1),
   FIS_FLIGHT_I         CHAR(7),
   FIS_DATE_I           CHAR(5),
   FIS_CITY_I           CHAR(3),
   FIS_CLASS_I          CHAR(1),
   FIS_SEAT             CHAR(3),
   FIS_MEAL             CHAR(4),
   FIS_FFPIND           CHAR(1),
   FIS_FILLER2          CHAR(8),
   FIS_CNAME            NVARCHAR2(50),
   FIS_FLNBR            CHAR(1),
   FIS_BOARD            CHAR(1),
   FIS_APPCODE          CHAR(10),
   FIS_UDFLAG           CHAR(1),
   FIS_UDFLAG2          CHAR(1),
   FIS_PID              CHAR(10),
   FIS_AF20             CHAR(1),
   FST_FILLER3          CHAR(13),
   FIS_PMARK            CHAR(1),
   FIS_FFLNBR           INT,
   FIS_CFFNBR           INT,
   FIS_CODENAME         VARCHAR(20)
                                           not null,
   FIS_CRTTS            TIMESTAMP
                                             not null,
   FIS_CHGID            VARCHAR(10),
   FIS_CHGTS            TIMESTAMP,
   constraint PK_FLOWNHIS primary key (FIS_NBR)
);

/*==============================================================*/
/* Table: BATCH_LOG                                              */
/*==============================================================*/
create table batch_log (
   JOB_OID	INTEGER	PRIMARY KEY     NOT NULL,
   ACTOR_ID	VARCHAR2(100)	NOT NULL,
   JOB_START	TIMESTAMP	NOT NULL,
   JOB_END	TIMESTAMP	NOT NULL,
   JOB_TIME	INTEGER	NOT NULL
);

/*==============================================================*/
/* Table: FLOWNMST1                                             */
/*==============================================================*/
create table FLOWNMST1 (
   FST1_NBR	INTEGER	PRIMARY KEY     NOT NULL,
   FST1_CARRIER	CHAR(3),
   FST1_FLIGHT	CHAR(4),
   FST1_SDATE	DATE,
   FST1_PORT_FD	CHAR(4),
   FST1_PASSNAME CHAR(50),
   FST1_SALESC CHAR(1),
   FST1_PORT_FA CHAR(3),
   FST1_SECNBR CHAR(3),
   FST1_STATUS CHAR(3),
   FST1_ET CHAR(13),
   FST1_CODENAME VARCHAR(20),
   FST1_CRTID VARCHAR(10),
	FST1_CRTTS TIMESTAMP,
	FST1_CHGID VARCHAR(10),
	FST1_CHGTS TIMESTAMP
);

/*==============================================================*/
/* Table: FLOWNMST2                                             */
/*==============================================================*/
create table FLOWNMST2 (
   FST2_NBR	INTEGER	PRIMARY KEY     NOT NULL,
   FST2_FST1_NBR INTEGER,
   FST2_BASIS	CHAR(12),
   FST2_FCARRIER CHAR(3),
   FST2_MARKET CHAR(7),
   FST2_PNR CHAR(6),
   FST2_MARK CHAR(4),
   FST2_SEAT CHAR(3), 
   FST2_CNAME CHAR(12),
   FST2_FLNBR CHAR(1),
   FST2_APPCODE CHAR(10),
   FST2_PID CHAR(10),
   FST2_AF20 CHAR(1),
   FST2_CODENAME VARCHAR(20),
   FST2_CRTID VARCHAR(10),
   FST2_CRTTS TIMESTAMP,
   FST2_CHGID VARCHAR(10),
   FST2_CHGTS TIMESTAMP
);
