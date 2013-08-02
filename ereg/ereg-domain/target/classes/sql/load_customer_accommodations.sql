-- CBT Accommodations
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('1','Extended Time',1);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('2','Color Contrast',2);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('3','Large Font/Magnification',3);
--PBT Accommodations
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('4','Audiocassette or CD recording version of the test',4);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('5','Braille',5);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('6','Large Print',6);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('7','Additional breaks',7);

insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('8','Test center location with wheelchair access',8);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('9','Separate Room',9);
insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('10','Calculator/Talking Calculator',10);

insert into ACMDTN_TYP(acmdtn_typ_cde,ACMDTN_TYP_DSC,ACMDTN_TYP_DSPLY_SEQ) values ('90','Other Accommodation',11);


insert into ACMDTN_TYP_VAL(ACMDTN_TYP_VAL_ID_NO,ACMDTN_TYP_VAL_DSPLY_SEQ,ACMDTN_TYP_VAL_UI_TXT,DSPLY_DTA_FLG,LBL,SCHDLG_SVC_ACMDTN_VAL,VAL,ACMDTN_TYP_CDE) values (1,1,'EXTENDED TIME','Y','50%','EXTENDED TIME','1.5','1');
insert into ACMDTN_TYP_VAL(ACMDTN_TYP_VAL_ID_NO,ACMDTN_TYP_VAL_DSPLY_SEQ,ACMDTN_TYP_VAL_UI_TXT,DSPLY_DTA_FLG,LBL,SCHDLG_SVC_ACMDTN_VAL,VAL,ACMDTN_TYP_CDE) values (2,2,'EXTENDED TIME','Y','100%','EXTENDED TIME','2','1');


insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','1','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','2','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','3','CBT');

insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','4','PBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','5','PBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','6','PBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','7','PBT');

insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','8','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','8','PBT');

insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','9','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','9','PBT');

insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','10','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','10','PBT');

insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','90','CBT');
insert into PGM_ACMDTN_DLVY_MDE(PGM_CDE,ACMDTN_TYP_CDE,DLVY_MDE_CDE) values('HSE','90','PBT');


insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (1,'C',1,'Technical Accommodations','Please select all the choices that apply:');
insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (2,'C',2,'Specialized Assistance','Please select all the choices that apply:');
insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (3,'C',3,'Adaptive Accommodations','Please select all the choices that apply:');
insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (4,'C',4,'Extended Testing Time','Please select all the choices that apply:');
insert into acmdtn_grp (ACMDTN_GRP_ID_NO,DSPLY_TYP_CDE,ACMDTN_GRP_SEQ,ACMDTN_GRP_DSC,ACMDTN_GRP_UI_TXT) values (5,'C',5,'Other Accommodations','Please select all the choices that apply:');

insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (1,'2','Technical Accommodations',1,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (1,'3','Technical Accommodations',2,'Y');

insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (4,'1','Extended Testing Time',1,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (4,'7','Extended Testing Time',2,'Y');

insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (3,'4','Adaptive Accommodations',1,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (3,'5','Adaptive Accommodations',2,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (3,'6','Adaptive Accommodations',3,'Y');

insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (5,'8','Other Accommodations',1,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (5,'9','Other Accommodations',2,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (5,'10','Other Accommodations',3,'Y');
insert into acmdtn_typ_grp (ACMDTN_GRP_ID_NO,ACMDTN_TYP_CDE,ACMDTN_TYP_GRP_DSC,ACMDTN_TYP_GRP_DSPLY_SEQ,DSPLY_DTA_FLG) values (5,'90','Other Accommodations',4,'Y');
