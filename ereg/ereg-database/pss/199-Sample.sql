
drop sequence seq_frm;
create sequence seq_frm;
insert into FRM(frm_id,tst_id,eff_dte,
frm_dsc)
values
(
seq_frm.NEXTVAL,
1,
'8-FEB-2013',
'This is sample form'
);

drop sequence seq_tsk;
create sequence seq_tsk;
insert into TSK
(TSK_ID,                         
TST_ID,
FRM_ID,
TSK_SEQ,
TSK_OPN_DTE,                    
TSK_CLOS_DTE
)
values(
seq_tsk.NEXTVAL,
1,
1,
1,
'8-MAR-2013',
'8-APR-2013'
);

insert into ASGND_TSK
(
CUSTOMER_ID,              
TSK_ID,                   
BKNG_ID,                        
TST_TKR_TST_ID,
APNTMT_DT,                
APPT_TM_ENT_FLG,
ETS_APNTMT_ID)
values(
1503,
1,
12345,
1,
'21-FEB-2009 09:00:00 AM EST',
'Y',
123456
);

update asgnd_tsk
set LANG_CDE='EN'
where CUSTOMER_ID=1503

drop sequence seq_prmpt;
create sequence seq_prmpt;
insert into PRMPT
(PRMPT_ID,
FRM_ID,
PRMPT_ACSN_ID,
PGM_CDE,
PRMPT_CLSTR_ID,
PRMPT_VRSN_NO)
values(
seq_prmpt.NEXTVAL,
1,
'123456',
'HSE',
12345,
1);

insert into PRMPT
(PRMPT_ID,
FRM_ID,
PRMPT_ACSN_ID,
PGM_CDE,
PRMPT_CLSTR_ID,
PRMPT_VRSN_NO)
values(
seq_prmpt.NEXTVAL,
1,
'123457',
'HSE',
12345,
1);

create sequence seq_prmpt_cntnt;

insert into PRMPT_CNTNT
(PRMPT_ID,
PRMPT_CNTNT,
PGM_CDE,
MDA_TYP_CDE)
values
(3,
'54686973206973206A7573742073616D706C652070726F6D707420636F6E74656E74',
'HSE',
'txt'
);

insert into cust_cr
(customer_id,
prmpt_id,
tsk_id)
values(
1503,
1,
1);

create sequence seq_cr_blb;

insert into CR_BLB
(
CR_BLB_ID,
customer_id,
prmpt_id,
tsk_id,
cr_prt_id,
cr_seq,
pgm_cde,
cr_blb,
MDA_TYP_CDE,
MDA_FRMT_TYP_CDE)
values
(
seq_cr_blb.NEXTVAL,
1503,
1,
1,
1,
1,
'HSE',
'54686973206973206A7573742073616D706C6520657373617920636F6E74656E74',
'txt',
'txt')



update cr_blb
set cr_blb='54686973206973206A7573742073616D706C6520657373617920636F6E74656E74'
where  cr_blb_id=1

create sequence seq_mda_mime_typ

insert into mda_mime_typ
(mime_typ_id,
mime_typ_nam)
values
(
seq_mda_mime_typ.NEXTVAL,
'text/plain'
);

insert into mda_mime_typ
(mime_typ_id,
mime_typ_nam)
values
(
seq_mda_mime_typ.NEXTVAL,
'application/pdf'
);

insert into mda_mime_typ
(mime_typ_id,
mime_typ_nam)
values
(
seq_mda_mime_typ.NEXTVAL,
'application/msword'
);

insert into mda_mime_typ
(mime_typ_id,
mime_typ_nam)
values
(
seq_mda_mime_typ.NEXTVAL,
'application/vnd.ms-excel'
);

insert into mda_mime_typ
(mime_typ_id,
mime_typ_nam)
values
(
seq_mda_mime_typ.NEXTVAL,
'application/vnd.ms-powerpoint'
);

insert into mda_mime_typ
(mime_typ_id,
mime_typ_nam)
values
(
seq_mda_mime_typ.NEXTVAL,
'unknown'
);


create sequence seq_doc;

select * from doc;

select * from mda_mime_typ;
commit;

