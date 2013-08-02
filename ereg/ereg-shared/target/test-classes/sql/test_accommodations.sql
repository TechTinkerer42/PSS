-- CBT Accommodations
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('1','Extended Time',1);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('2','Color Contrast',2);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('3','Large Font/Magnification',3);
--PBT Accommodations
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('4','Audio Version',4);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('5','Braille',5);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('6','Large Print',6);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('90','Other Accommodation',7);

insert into ACMDTN_TYP_VAL(ACMDTN_TYP_VAL_ID_NO,ACMDTN_TYP_VAL_DSPLY_SEQ,ACMDTN_TYP_VAL_UI_TXT,DSPLY_DTA_FLG,LBL,SCHDLG_SVC_ACMDTN_VAL,VAL,ACMDTN_TYP_CDE) values (1,1,'EXTENDED TIME','Y','50%','EXTENDED TIME','1.5','1');
insert into ACMDTN_TYP_VAL(ACMDTN_TYP_VAL_ID_NO,ACMDTN_TYP_VAL_DSPLY_SEQ,ACMDTN_TYP_VAL_UI_TXT,DSPLY_DTA_FLG,LBL,SCHDLG_SVC_ACMDTN_VAL,VAL,ACMDTN_TYP_CDE) values (2,2,'EXTENDED TIME','Y','100%','EXTENDED TIME','2','1');

Insert into HIER_TYP (HIER_TYP_CDE,DSC) values ('TST','Hierarchy Test');
Insert into EREG_HIER (EREG_HIER_ID,HIER_NAM,PARNT_EREG_HIER_ID,HIER_TYP_CDE,HIER_LVL) values (1,'HSET_TEST',null,'TST',1);

INSERT INTO TST (PGM_CDE, TST_ID, DSPLY_DTA_FLG, TST_NAM,TST_DURN,SCHDLG_DURN) VALUES ('HSE', 1, 'Y', 'ELA-Reading',150,150);
INSERT INTO TST (PGM_CDE, TST_ID, DSPLY_DTA_FLG, TST_NAM,TST_DURN,SCHDLG_DURN) VALUES ('HSE', 2, 'Y', 'ELA-Writing (MC Section)',150,150);
INSERT INTO TST (PGM_CDE, TST_ID, DSPLY_DTA_FLG, TST_NAM,TST_DURN,SCHDLG_DURN) VALUES ('HSE', 6, 'Y', 'ELA-Writing (CR Section)',150,150);
INSERT INTO TST (PGM_CDE, TST_ID, DSPLY_DTA_FLG, TST_NAM,TST_DURN,SCHDLG_DURN) VALUES ('HSE', 3, 'Y', 'Social Studies',150,150);
INSERT INTO TST (PGM_CDE, TST_ID, DSPLY_DTA_FLG, TST_NAM,TST_DURN,SCHDLG_DURN) VALUES ('HSE', 4, 'Y', 'Science',150,150);
INSERT INTO TST (PGM_CDE, TST_ID, DSPLY_DTA_FLG, TST_NAM,TST_DURN,SCHDLG_DURN) VALUES ('HSE', 5, 'Y', 'Mathematics',150,150);


INSERT INTO DLVY_MDE (DLVY_MDE_CDE, DLVY_MDE_DSC) VALUES ('CBT', 'Computer-based.');

insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','1','PBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','2','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','3','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','4','PBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','5','PBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','6','PBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','90','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','90','PBT');

insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (1,'C',1,'Paper Based Accommodations','Please select all the choices that apply:');
insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (2,'C',2,'Computer Based Accommodations','Please select all the choices that apply:');
insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (3,'C',3,'Other Accommodations','Please select all the choices that apply:');

insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (1,'1','Computer Based Test',1,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (1,'2','Computer Based Test',2,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (1,'3','Computer Based Test',3,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (2,'4','Paper Based Test',1,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (2,'5','Paper Based Test',2,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (2,'6','Paper Based Test',3,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (3,'90','Other Accommodation',1,'Y');
