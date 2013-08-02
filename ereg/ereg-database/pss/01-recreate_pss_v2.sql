/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2 (ETS)                   */
/* Created on:     5/23/2013 3:02:57 PM                         */
/*==============================================================*/

drop sequence ADM_ID_SEQ;

create sequence ADM_ID_SEQ
increment by 1
start with 1;

drop sequence CR_BLB_ID_SEQ;

create sequence CR_BLB_ID_SEQ
increment by 1
start with 1;

drop sequence PKG_NO_SEQ;

create sequence PKG_NO_SEQ
increment by 1
start with 1;

drop sequence PRMPT_ID_SEQ;

create sequence PRMPT_ID_SEQ
increment by 1
start with 1;

drop table ADM CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: ADM                                                   */
/*==============================================================*/
create table ADM  (
   PGM_CDE              VARCHAR2(5)                     not null,
   ADM_CDE              VARCHAR2(9)                     not null,
   ADM_NAM              VARCHAR2(50)                    not null,
   ADM_DSC              VARCHAR2(500),
   ADM_STRT_DTE         DATE                            not null,
   ADM_END_DTE          DATE,
   ADM_STS_CDE          VARCHAR2(5)                     not null,
   WKFLW_STEP_CDE       VARCHAR2(5),
   REF_ADM_FLG          CHAR                           default 'N' not null
      constraint CKC_REF_ADM_FLG_ADM check (REF_ADM_FLG in ('Y','N')),
   ADM_TYP_CDE          VARCHAR2(5)                     not null,
   LCK_DTM              DATE,
   LCK_LGN_UID          VARCHAR2(32),
   CMNT_XML             SYS.XMLTYPE,
   RDY_FOR_USE_FLG      CHAR                           default 'N' not null
      constraint CKC_RDY_FOR_USE_FLG_ADM check (RDY_FOR_USE_FLG in ('Y','N')),
   CRET_DTM             DATE                            not null,
   CRET_LGN_UID         VARCHAR2(32)                    not null,
   LST_UPDT_DTM         DATE,
   LST_UPDT_LGN_UID     VARCHAR2(32),
   DEL_FLG              VARCHAR2(1)                    default 'N' not null
      constraint CKC_DEL_FLG check (DEL_FLG in ('Y','N')),
   AD_HOC_WKFLW_STEP_CDE VARCHAR2(5),
   ERR_APRVL_XML        SYS.XMLTYPE,
   QC_LCK_REQD_FLG      VARCHAR2(1)                    default 'N' not null
      constraint QC_LCK_REQD_FLG_CK1 check (QC_LCK_REQD_FLG in ('Y','N')),
   OBJ_WKFLW_CNFGN_ID   NUMBER,
   DLVY_MDE_CDE         VARCHAR2(5),
   MGRT_FLG             CHAR(1)                        default 'N' not null
      constraint MGRT_FLG_CK1 check (MGRT_FLG in ('Y','N')),
   MGRT_DTE             DATE,
   MGRT_SRC_NAM         VARCHAR2(50),
   ADM_ID               NUMBER                          not null,
   constraint ADM_PK primary key (PGM_CDE, ADM_CDE),
   constraint ADM_AK1 unique (ADM_ID)
);

/*==============================================================*/
/* Index: PGM_FK1                                               */
/*==============================================================*/
create index PGM_FK1 on ADM (
   PGM_CDE ASC
);

/*==============================================================*/
/* Index: OBJ_WKFLW_CNFGN_FK3                                   */
/*==============================================================*/
create index OBJ_WKFLW_CNFGN_FK3 on ADM (
   OBJ_WKFLW_CNFGN_ID ASC
);

drop table ASGND_TSK CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: ASGND_TSK                                             */
/*==============================================================*/
create table ASGND_TSK  (
   CUSTOMER_ID          NUMBER(19)                      not null,
   TSK_ID               NUMBER                          not null,
   DOC_STS_TYP_CDE      VARCHAR2(5),
   DRAFT_DTE            DATE,
   SBMT_DTE             DATE,
   BKNG_ID              NUMBER                          not null,
   TST_TKR_TST_ID       NUMBER                          not null,
   APNTMT_DT            TIMESTAMP WITH TIME ZONE        not null,
   APPT_TM_ENT_FLG      VARCHAR2(1)                    default 'Y' not null
      constraint APPT_TM_ENT_FLG_CK1 check (APPT_TM_ENT_FLG in ('Y','N')),
   ETS_APNTMT_ID        VARCHAR2(16)                    not null,
   EXTRNL_APNTMT_ID     VARCHAR2(36),
   BKNG_STS_TYP_CDE     VARCHAR2(5),
   DLVY_MDE_CDE         VARCHAR2(5),
   TST_CNTR_ID_NO       NUMBER,
   EVNT_OTCM_STS_TYP_CDE VARCHAR2(4),
   CUST_FST_NAM         VARCHAR2(30),
   CUST_MID_NAM         VARCHAR2(50),
   CUST_LST_NAM         VARCHAR2(50),
   CUST_BRTH_DT         DATE,
   TST_LNCH_TM          DATE,
   TST_PKG_ID           VARCHAR2(23),
   TST_DURN             NUMBER,
   REGN_SYS_ID          VARCHAR2(3),
   CMNT                 VARCHAR2(2000),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   TST_ID               NUMBER,
   LANG_CDE             VARCHAR2(5),
   APPT_DTE_STR         VARCHAR2(20),
   constraint ASGND_TSK_PK primary key (CUSTOMER_ID, TSK_ID)
);

drop table ASGND_TSK_DOC CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: ASGND_TSK_DOC                                         */
/*==============================================================*/
create table ASGND_TSK_DOC  (
   ASGND_TSK_DOC_ID     NUMBER                          not null,
   CUSTOMER_ID          NUMBER(19),
   TSK_ID               NUMBER,
   DOC_BLB              BLOB,
   RSP_BLB_LCTN_ADR     VARCHAR2(500),
   RSP_SRC_LCTN_NAM     VARCHAR2(100),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint ASGND_TSK_DOC_PK primary key (ASGND_TSK_DOC_ID)
);

/*==============================================================*/
/* Index: ASGND_TSK_FK1                                         */
/*==============================================================*/
create index ASGND_TSK_FK1 on ASGND_TSK_DOC (
   CUSTOMER_ID ASC,
   TSK_ID ASC
);

drop table BLC_ADMIN_USER CASCADE CONSTRAINTS PURGE;


/*==============================================================*/
/* Table: BLC_ADMIN_USER                                        */
/*==============================================================*/
create table BLC_ADMIN_USER  (
   ADMIN_USER_ID        NUMBER(19)                      not null,
   ACTIVE_STATUS_FLAG   NUMBER(1),
   EMAIL                VARCHAR2(255 char)              not null,
   LOGIN                VARCHAR2(255 char)              not null,
   NAME                 VARCHAR2(255 char)              not null,
   PASSWORD             VARCHAR2(255 char)              not null,
   PHONE_NUMBER         VARCHAR2(20),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint BLC_ADMIN_USR_PK primary key (ADMIN_USER_ID)
);

/*==============================================================*/
/* Index: ADMINPERM_EMAIL_INDEX                                 */
/*==============================================================*/
create index ADMINPERM_EMAIL_INDEX on BLC_ADMIN_USER (
   EMAIL ASC
);

/*==============================================================*/
/* Index: ADMINUSER_NAME_INDEX                                  */
/*==============================================================*/
create index ADMINUSER_NAME_INDEX on BLC_ADMIN_USER (
   NAME ASC
);

drop table BLC_CUSTOMER CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: BLC_CUSTOMER                                          */
/*==============================================================*/
create table BLC_CUSTOMER  (
   CUSTOMER_ID          NUMBER(19)                      not null,
   CREATED_BY           NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   DATE_UPDATED         TIMESTAMP,
   UPDATED_BY           NUMBER(19),
   CHALLENGE_ANSWER     VARCHAR2(255 char),
   DEACTIVATED          NUMBER(1),
   EMAIL_ADDRESS        VARCHAR2(100 char),
   FIRST_NAME           VARCHAR2(30 char),
   LAST_NAME            VARCHAR2(50 char),
   PASSWORD             VARCHAR2(255 char),
   PASSWORD_CHANGE_REQUIRED NUMBER(1),
   RECEIVE_EMAIL        NUMBER(1),
   IS_REGISTERED        NUMBER(1),
   USER_NAME            VARCHAR2(255 char),
   CHALLENGE_QUESTION_ID NUMBER(19),
   LOCALE_CODE          VARCHAR2(255 char),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   constraint BLC_CUST_PK primary key (CUSTOMER_ID),
   constraint BLC_CUST_AK1 unique (USER_NAME)
);

/*==============================================================*/
/* Index: CUSTOMER_CHALLENGE_INDEX                              */
/*==============================================================*/
create index CUSTOMER_CHALLENGE_INDEX on BLC_CUSTOMER (
   CHALLENGE_QUESTION_ID ASC
);

/*==============================================================*/
/* Index: CUSTOMER_EMAIL_INDEX                                  */
/*==============================================================*/
create index CUSTOMER_EMAIL_INDEX on BLC_CUSTOMER (
   EMAIL_ADDRESS ASC
);

drop table CR_BLB CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: CR_BLB                                                */
/*==============================================================*/
create table CR_BLB  (
   CR_BLB_ID            NUMBER                          not null,
   CUSTOMER_ID          NUMBER(19),
   PRMPT_ID             NUMBER,
   TSK_ID               NUMBER,
   CR_PRT_ID            NUMBER                          not null,
   CR_SEQ               NUMBER                          not null,
   RSP_BLB_LCTN_ADR     VARCHAR2(500),
   PGM_CDE              VARCHAR2(5)                     not null,
   CR_BLB               BLOB,
   RSP_SRC_LCTN_NAM     VARCHAR2(100),
   MDA_TYP_CDE          VARCHAR2(5)                     not null,
   MDA_FRMT_TYP_CDE     VARCHAR2(5)                     not null,
   RSP_LNGTH_NO         NUMBER,
   IMG_DOC_ID           VARCHAR2(8),
   IMG_FLE_NO           VARCHAR2(16),
   BAT_FLE_NO           VARCHAR2(20),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint CR_BLB_PK primary key (CR_BLB_ID)
);

/*==============================================================*/
/* Index: PGM_FK17                                              */
/*==============================================================*/
create index PGM_FK17 on CR_BLB (
   PGM_CDE ASC
);

/*==============================================================*/
/* Index: CUST_CR_FK2                                           */
/*==============================================================*/
create index CUST_CR_FK2 on CR_BLB (
   CUSTOMER_ID ASC,
   PRMPT_ID ASC,
   TSK_ID ASC
);

drop table CR_CMNT CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: CR_CMNT                                               */
/*==============================================================*/
create table CR_CMNT  (
   CUSTOMER_ID          NUMBER(19)                      not null,
   PRMPT_ID             NUMBER                          not null,
   TSK_ID               NUMBER                          not null,
   CMNT_BY_ADMIN_USR_ID NUMBER(19),
   CR_CMNT_TXT          VARCHAR2(2000)                  not null,
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint CR_CMNT_PK primary key (CUSTOMER_ID, PRMPT_ID, TSK_ID)
);

drop table CUST_CR CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: CUST_CR                                               */
/*==============================================================*/
create table CUST_CR  (
   CUSTOMER_ID          NUMBER(19)                      not null,
   PRMPT_ID             NUMBER                          not null,
   TSK_ID               NUMBER                          not null,
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint CUST_CR_PK primary key (CUSTOMER_ID, PRMPT_ID, TSK_ID)
);



/*==============================================================*/
/* Index: PRMPT_FK1                                             */
/*==============================================================*/
create index PRMPT_FK1 on CUST_CR (
   PRMPT_ID ASC
);


drop table CUST_CR_ASGND_TSK_DOC CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: CUST_CR_ASGND_TSK_DOC                                 */
/*==============================================================*/
create table CUST_CR_ASGND_TSK_DOC  (
   ASGND_TSK_DOC_ID     NUMBER                          not null,
   CUSTOMER_ID          NUMBER(19)                      not null,
   PRMPT_ID             NUMBER                          not null,
   TSK_ID               NUMBER                          not null,
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint CUST_CR_ASGND_TSK_DOC_PK primary key (ASGND_TSK_DOC_ID, CUSTOMER_ID, PRMPT_ID, TSK_ID)
);

drop table DLVY_MDE CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: DLVY_MDE                                              */
/*==============================================================*/
create table DLVY_MDE  (
   DLVY_MDE_CDE         VARCHAR2(5)                     not null,
   DLVY_MDE_DSC         VARCHAR2(100)                   not null,
   DLVY_MDE_DSPLY_SEQ   NUMBER,
   DSPLY_DTA_FLG        VARCHAR2(1)                    default 'Y' not null
      constraint DSPLY_DTA_FLG_CK21 check (DSPLY_DTA_FLG in ('Y','N')),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint DLVY_MDE_PK primary key (DLVY_MDE_CDE)
);


drop table DOC CASCADE CONSTRAINTS PURGE;
/*==============================================================*/
/* Table: DOC                                                   */
/*==============================================================*/
create table DOC  (
   DOC_ID               NUMBER                          not null,
   CUSTOMER_ID          NUMBER(19)                      not null,
   MIME_TYP_ID          NUMBER,
   DOC_BLB              BLOB                            not null,
   RSP_BLB_LCTN_ADR     VARCHAR2(500),
   RSP_SRC_LCTN_NAM     VARCHAR2(100),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint DOC_PK primary key (DOC_ID)
);

/*==============================================================*/
/* Index: ETS_CUST_FK1                                          */
/*==============================================================*/
create index ETS_CUST_FK1 on DOC (
   CUSTOMER_ID ASC
);

drop table DOC_STS_TYP CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: DOC_STS_TYP                                           */
/*==============================================================*/
create table DOC_STS_TYP  (
   DOC_STS_TYP_CDE      VARCHAR2(5)                     not null,
   DOC_STS_TYP_DSC      VARCHAR2(175)                   not null,
   DOC_STS_TYP_DSPLY_SEQ NUMBER,
   DSPLY_DTA_FLG        VARCHAR2(1)                    default 'Y' not null
      constraint DSPLY_DTA_FLG_CK9 check (DSPLY_DTA_FLG in ('Y','N')),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint DOC_STS_TYP_PK primary key (DOC_STS_TYP_CDE)
);

drop table ETS_ADM_USR CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: ETS_ADM_USR                                           */
/*==============================================================*/
create table ETS_ADM_USR  (
   ADMIN_USER_ID        NUMBER(19)                      not null,
   FIRST_NAME           VARCHAR2(30 char),
   MID_NAM              VARCHAR2(50),
   LAST_NAME            VARCHAR2(50 char),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   ADM_IDFN_TXT         VARCHAR2(50),
   LDAP_GUID_ID         VARCHAR2(40),
   BRTH_DTE             DATE,
   CHALLENGE_QUESTION_ID NUMBER(19),
   CHALLENGE_ANSWER     VARCHAR2(255 char),
   GNDR_CDE             VARCHAR2(5),
   INTRNL_USR_FLG       VARCHAR2(1)                    default 'Y' not null
      constraint INTRNL_USR_FLG_CK2 check (INTRNL_USR_FLG in ('Y','N')),
   constraint ETS_ADM_USR_PK primary key (ADMIN_USER_ID)
);


drop table ETS_CUST CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: ETS_CUST                                              */
/*==============================================================*/
create table ETS_CUST  (
   CUSTOMER_ID          NUMBER(19)                      not null,
   CUST_TYP_CDE         VARCHAR2(5)                     not null,
   ETNCTY_CDE           VARCHAR2(5),
   MIL_STS_CDE          VARCHAR2(5),
   PRFRD_TST_TAKING_LANG_CDE VARCHAR2(5),
   PRMRY_SPKNG_LANG_CDE VARCHAR2(5),
   GNDR_CDE             VARCHAR2(5),
   MID_NAM              VARCHAR2(50),
   BRTH_DTE             DATE,
   SSN_LST_4            VARCHAR2(4),
   TAX_EXMPT_FLG        VARCHAR2(1)                    default 'N' not null
      constraint TAX_EXMPT_FLG_CK1 check (TAX_EXMPT_FLG in ('Y','N')),
   TRMS_ACEPTNC_DTM     TIMESTAMP,
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   LDAP_GUID_ID         NUMBER,
   SRC_ID               NUMBER,
   INTRNL_USR_FLG       VARCHAR2(1)                    default 'N' not null
      constraint INTRNL_USR_FLG_CK1 check (INTRNL_USR_FLG in ('Y','N')),
   constraint ETS_CUST_PK primary key (CUSTOMER_ID)
);

/*==============================================================*/
/* Index: ETS_CUST_TYP_FK1                                      */
/*==============================================================*/
create index ETS_CUST_TYP_FK1 on ETS_CUST (
   CUST_TYP_CDE ASC
);

/*==============================================================*/
/* Index: ETNCTY_FK1                                            */
/*==============================================================*/
create index ETNCTY_FK1 on ETS_CUST (
   ETNCTY_CDE ASC
);

/*==============================================================*/
/* Index: GNDR_FK1                                              */
/*==============================================================*/
create index GNDR_FK1 on ETS_CUST (
   GNDR_CDE ASC
);

/*==============================================================*/
/* Index: MIL_STS_FK1                                           */
/*==============================================================*/
create index MIL_STS_FK1 on ETS_CUST (
   MIL_STS_CDE ASC
);

/*==============================================================*/
/* Index: LANG_FK1                                              */
/*==============================================================*/
create index LANG_FK1 on ETS_CUST (
   PRFRD_TST_TAKING_LANG_CDE ASC
);

drop table FRM CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: FRM                                                   */
/*==============================================================*/
create table FRM  (
   FRM_ID               NUMBER                          not null,
   TST_ID               NUMBER                          not null,
   LANG_CDE             VARCHAR2(5),
   ACMDTN_TYP_CDE       VARCHAR2(5),
   PARNT_FRM_ID         NUMBER,
   FRM_TYP_CDE          VARCHAR2(5),
   PGM_CDE              VARCHAR2(5),
   ADM_CDE              VARCHAR2(9),
   PKG_NO               NUMBER,
   DLVY_MDE_CDE         VARCHAR2(5),
   EFF_DTE              DATE                            not null,
   EXPRTN_DTE           DATE,
   FRM_DSC              VARCHAR2(175)                   not null,
   FRM_CDE              VARCHAR2(20),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint FRM_PK primary key (FRM_ID)
);

/*==============================================================*/
/* Index: FRM_TYP_FK1                                           */
/*==============================================================*/
create index FRM_TYP_FK1 on FRM (
   FRM_TYP_CDE ASC
);

/*==============================================================*/
/* Index: FRM_ACMDTN_TYP_FK1                                    */
/*==============================================================*/
create index FRM_ACMDTN_TYP_FK1 on FRM (
   ACMDTN_TYP_CDE ASC
);

drop table FRM_TYP CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: FRM_TYP                                               */
/*==============================================================*/
create table FRM_TYP  (
   FRM_TYP_CDE          VARCHAR2(5)                     not null,
   FRM_TYP_DSC          VARCHAR2(175)                   not null,
   FRM_TYP_DSPLY_SEQ    NUMBER,
   DSPLY_DTA_FLG        VARCHAR2(1)                    default 'Y' not null
      constraint DSPLY_DTA_FLG_CK8 check (DSPLY_DTA_FLG in ('Y','N')),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint FRM_TYP_PK primary key (FRM_TYP_CDE)
);

drop table LANG CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: LANG                                                  */
/*==============================================================*/
create table LANG  (
   LANG_CDE             VARCHAR2(5)                     not null,
   LANG_DSC             VARCHAR2(175)                   not null,
   LANG_DSPLY_SEQ       NUMBER,
   DSPLY_DTA_FLG        VARCHAR2(1)                    default 'Y' not null
      constraint DSPLY_DTA_FLG_CK27 check (DSPLY_DTA_FLG in ('Y','N')),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint LANG_PK primary key (LANG_CDE)
);

drop table MDA_MIME_TYP CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: MDA_MIME_TYP                                          */
/*==============================================================*/
create table MDA_MIME_TYP  (
   MIME_TYP_ID          NUMBER                          not null,
   MIME_TYP_NAM         VARCHAR2(50)                    not null,
   MIME_TYP_DSC         VARCHAR2(100),
   FLE_EXTNSN_DSC       VARCHAR2(50),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint MDA_MIME_TYP_PK primary key (MIME_TYP_ID)
);

drop table PGM CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: PGM                                                   */
/*==============================================================*/
create table PGM  (
   PGM_CDE              VARCHAR2(5)                     not null,
   PGM_NAM              VARCHAR2(50),
   PGM_DSPLY_SEQ        NUMBER,
   DSPLY_DTA_FLG        VARCHAR2(1)                    default 'Y' not null
      constraint DSPLY_DTA_FLG_CK1 check (DSPLY_DTA_FLG in ('Y','N')),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   EREG_HIER_ID         NUMBER,
   constraint PGM_PK primary key (PGM_CDE)
);

drop table PKG CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: PKG                                                   */
/*==============================================================*/
create table PKG  (
   TST_ID               NUMBER                          not null,
   PKG_NO               NUMBER                          not null,
   TST_FRM_TMPLT_SET_NO NUMBER,
   PKG_TYP_CDE          VARCHAR2(20),
   PKG_NAM              VARCHAR2(50)                    not null,
   PKG_DSC              VARCHAR2(100),
   CMNT_XML             SYS.XMLTYPE,
   PKG_STS_CDE          VARCHAR2(5)                     not null,
   WKFLW_STEP_CDE       VARCHAR2(5),
   PKG_IBT_PKG_CDE      VARCHAR2(23),
   LCK_DTM              DATE,
   LCK_LGN_UID          VARCHAR2(32),
   NOT_RDY_FOR_USE_EFF_DTE DATE,
   NOT_RDY_FOR_USE_RSN_CDE VARCHAR2(5),
   NOT_RDY_FOR_USE_RSN_TXT VARCHAR2(256),
   RDY_FOR_USE_FLG      CHAR                            not null
      constraint CKC_RDY_FOR_USE_FLG_PKG check (RDY_FOR_USE_FLG in ('Y','N')),
   CRET_DTM             DATE                            not null,
   CRET_LGN_UID         VARCHAR2(32)                    not null,
   LST_UPDT_DTM         DATE,
   LST_UPDT_LGN_UID     VARCHAR2(32),
   NOT_RDY_FOR_USE_RSN_TYP_CDE VARCHAR2(5),
   DEL_FLG              VARCHAR2(1)                     not null
      constraint CKC_DEL_FLG_PKG check (DEL_FLG in ('Y','N')),
   AD_HOC_WKFLW_STEP_CDE VARCHAR2(5),
   OBJ_WKFLW_CNFGN_ID   NUMBER,
   MGRT_FLG             CHAR(1)                        default 'N' not null
      constraint MGRT_FLG_CK50 check (MGRT_FLG in ('Y','N')),
   MGRT_DTE             DATE,
   MGRT_SRC_NAM         VARCHAR2(50),
   STAT_APRVL_CDE       VARCHAR2(5),
   FRM_STRC_CNTNT_XML   SYS.XMLTYPE,
   constraint PKG_PK primary key (PKG_NO, TST_ID),
   constraint PKG_AK1 unique (PKG_NO)
);

/*==============================================================*/
/* Index: PKG_TYP_FK1                                           */
/*==============================================================*/
create index PKG_TYP_FK1 on PKG (
   PKG_TYP_CDE ASC
);

/*==============================================================*/
/* Index: TST_FRM_TMPLT_SET_FK1                                 */
/*==============================================================*/
create index TST_FRM_TMPLT_SET_FK1 on PKG (
   TST_FRM_TMPLT_SET_NO ASC
);

/*==============================================================*/
/* Index: OBJ_WKFLW_CNFGN_FK4                                   */
/*==============================================================*/
create index OBJ_WKFLW_CNFGN_FK4 on PKG (
   OBJ_WKFLW_CNFGN_ID ASC
);

drop table PRMPT CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: PRMPT                                                 */
/*==============================================================*/
create table PRMPT  (
   PRMPT_ID             NUMBER                          not null,
   FRM_ID               NUMBER,
   PRMPT_ACSN_ID        VARCHAR2(8)                     not null,
   PRMPT_NAM            VARCHAR2(256),
   PGM_CDE              VARCHAR2(5)                     not null,
   PRMPT_CLSTR_ID       NUMBER                          not null,
   PRMPT_SEQ            NUMBER,
   PRMPT_VRSN_NO        NUMBER                          not null,
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint PRMPT_PK primary key (PRMPT_ID),
   constraint PRMPT_AK1 unique (PRMPT_ACSN_ID)
);

/*==============================================================*/
/* Index: PGM_FK1022                                            */
/*==============================================================*/
create index PGM_FK1022 on PRMPT (
   PGM_CDE ASC
);

/*==============================================================*/
/* Index: PRMPT_CLSTR_FK1                                       */
/*==============================================================*/
create index PRMPT_CLSTR_FK1 on PRMPT (
   PRMPT_CLSTR_ID ASC
);

/*==============================================================*/
/* Index: FRM_FK1                                               */
/*==============================================================*/
create index FRM_FK1 on PRMPT (
   FRM_ID ASC
);

drop table PRMPT_CNTNT CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: PRMPT_CNTNT                                           */
/*==============================================================*/
create table PRMPT_CNTNT  (
   PRMPT_ID             NUMBER                          not null,
   PRMPT_CNTNT          BLOB                            not null,
   PGM_CDE              VARCHAR2(5)                     not null,
   MDA_TYP_CDE          VARCHAR2(5)                     not null,
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint PRMPT_CNTNT_PK primary key (PRMPT_ID)
);

/*==============================================================*/
/* Index: MDA_TYP_FK3                                           */
/*==============================================================*/
create index MDA_TYP_FK3 on PRMPT_CNTNT (
   PGM_CDE ASC,
   MDA_TYP_CDE ASC
);

drop table TSK CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: TSK                                                   */
/*==============================================================*/
create table TSK  (
   TSK_ID               NUMBER                          not null,
   TST_ID               NUMBER                          not null,
   FRM_ID               NUMBER                          not null,
   TSK_SEQ              NUMBER                          not null,
   TSK_OPN_DTE          DATE                            not null,
   TSK_CLOS_DTE         DATE                            not null,
   INSTR_TXT            BLOB,
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   constraint TSK_PK primary key (TSK_ID)
);


/*==============================================================*/
/* Index: TST_FK3                                               */
/*==============================================================*/
create index TST_FK3 on TSK (
   TST_ID ASC
);

drop table TST CASCADE CONSTRAINTS PURGE;

/*==============================================================*/
/* Table: TST                                                   */
/*==============================================================*/
create table TST  (
   TST_ID               NUMBER                          not null,
   PGM_CDE              VARCHAR2(5)                     not null,
   TST_NAM              VARCHAR2(50),
   TST_DSPLY_SEQ        NUMBER,
   DSPLY_DTA_FLG        VARCHAR2(1)                    default 'Y' not null
      constraint DSPLY_DTA_FLG_CK15 check (DSPLY_DTA_FLG in ('Y','N')),
   CREATED_BY_ADMIN_USR_ID NUMBER(19),
   CREATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_CREATED         TIMESTAMP,
   UPDATED_BY_ADMIN_USR_ID NUMBER(19),
   UPDATED_BY_CUSTOMER_ID NUMBER(19),
   DATE_UPDATED         TIMESTAMP,
   TST_DURN             NUMBER                          not null,
   SCHDLG_DURN          NUMBER                          not null,
   constraint TST_PK primary key (TST_ID)
);

/*==============================================================*/
/* Index: PGM_FK2                                               */
/*==============================================================*/
create index PGM_FK2 on TST (
   PGM_CDE ASC
);

alter table ADM
   add constraint DLVY_MDE_FK2 foreign key (DLVY_MDE_CDE)
      references DLVY_MDE (DLVY_MDE_CDE);

alter table ADM
   add constraint PGM_FK2 foreign key (PGM_CDE)
      references PGM (PGM_CDE);

alter table ASGND_TSK
   add constraint DLVY_MDE_FK1 foreign key (DLVY_MDE_CDE)
      references DLVY_MDE (DLVY_MDE_CDE);

alter table ASGND_TSK
   add constraint DOC_STS_TYP_FK1 foreign key (DOC_STS_TYP_CDE)
      references DOC_STS_TYP (DOC_STS_TYP_CDE);

alter table ASGND_TSK
   add constraint ETS_CUST_FK3 foreign key (CUSTOMER_ID)
      references ETS_CUST (CUSTOMER_ID);

alter table ASGND_TSK
   add constraint LANG_FK1 foreign key (LANG_CDE)
      references LANG (LANG_CDE);

alter table ASGND_TSK
   add constraint TSK_FK1 foreign key (TSK_ID)
      references TSK (TSK_ID);

alter table ASGND_TSK
   add constraint TST_FK2 foreign key (TST_ID)
      references TST (TST_ID);

alter table ASGND_TSK_DOC
   add constraint ASGND_TSK_FK1 foreign key (CUSTOMER_ID, TSK_ID)
      references ASGND_TSK (CUSTOMER_ID, TSK_ID);

alter table CR_BLB
   add constraint CUST_CR_FK2 foreign key (CUSTOMER_ID, PRMPT_ID, TSK_ID)
      references CUST_CR (CUSTOMER_ID, PRMPT_ID, TSK_ID);

alter table CR_CMNT
   add constraint CUST_CR_FK1 foreign key (CUSTOMER_ID, PRMPT_ID, TSK_ID)
      references CUST_CR (CUSTOMER_ID, PRMPT_ID, TSK_ID);

alter table CR_CMNT
   add constraint ETS_ADM_USR_FK1 foreign key (CMNT_BY_ADMIN_USR_ID)
      references ETS_ADM_USR (ADMIN_USER_ID);

alter table CUST_CR
   add constraint ETS_CUST_FK2 foreign key (CUSTOMER_ID)
      references ETS_CUST (CUSTOMER_ID);

alter table CUST_CR
   add constraint PRMPT_FK1 foreign key (PRMPT_ID)
      references PRMPT (PRMPT_ID);

alter table CUST_CR
   add constraint TSK_FK3 foreign key (TSK_ID)
      references TSK (TSK_ID);

alter table CUST_CR_ASGND_TSK_DOC
   add constraint ASGND_TSK_DOC_FK1 foreign key (ASGND_TSK_DOC_ID)
      references ASGND_TSK_DOC (ASGND_TSK_DOC_ID);

alter table CUST_CR_ASGND_TSK_DOC
   add constraint CUST_CR_FK3 foreign key (CUSTOMER_ID, PRMPT_ID, TSK_ID)
      references CUST_CR (CUSTOMER_ID, PRMPT_ID, TSK_ID);

alter table DOC
   add constraint ETS_CUST_FK1 foreign key (CUSTOMER_ID)
      references ETS_CUST (CUSTOMER_ID);

alter table DOC
   add constraint MDA_MIME_TYP_FK1 foreign key (MIME_TYP_ID)
      references MDA_MIME_TYP (MIME_TYP_ID);

alter table ETS_ADM_USR
   add constraint BLC_ADMIN_USR_FK1 foreign key (ADMIN_USER_ID)
      references BLC_ADMIN_USER (ADMIN_USER_ID);

alter table ETS_CUST
   add constraint BLC_CUST_FK1 foreign key (CUSTOMER_ID)
      references BLC_CUSTOMER (CUSTOMER_ID);

alter table FRM
   add constraint ADM_FK1 foreign key (PGM_CDE, ADM_CDE)
      references ADM (PGM_CDE, ADM_CDE);

alter table FRM
   add constraint FRM_FK2 foreign key (PARNT_FRM_ID)
      references FRM (FRM_ID);

alter table FRM
   add constraint FRM_TYP_FK1 foreign key (FRM_TYP_CDE)
      references FRM_TYP (FRM_TYP_CDE);

alter table FRM
   add constraint PKG_FK1 foreign key (PKG_NO, TST_ID)
      references PKG (PKG_NO, TST_ID);

alter table FRM
   add constraint TST_FK1 foreign key (TST_ID)
      references TST (TST_ID);

alter table PKG
   add constraint TST_FK4 foreign key (TST_ID)
      references TST (TST_ID);

alter table PRMPT
   add constraint FRM_FK1 foreign key (FRM_ID)
      references FRM (FRM_ID);

alter table PRMPT_CNTNT
   add constraint PRMPT_FK11 foreign key (PRMPT_ID)
      references PRMPT (PRMPT_ID);

alter table TSK
   add constraint FRM_FK3 foreign key (FRM_ID)
      references FRM (FRM_ID);

alter table TSK
   add constraint TST_FK3 foreign key (TST_ID)
      references TST (TST_ID);

alter table TST
   add constraint PGM_FK1 foreign key (PGM_CDE)
      references PGM (PGM_CDE);

