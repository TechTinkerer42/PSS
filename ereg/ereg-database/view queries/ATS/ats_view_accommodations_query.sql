CREATE VIEW ATS_TRNSCTN_ACMDTN_V AS
SELECT
  log.ATS_TRNSCTN_LOG_ID "ATS_TRNSCTN_LOG_ID",
  bkngAccmd.ACMDTN_TYP_CDE "ACMDTN_TYP_CDE",
  acmTyp.ACMDTN_TYP_DSC "ACMDTN_TYP_DSC",
  acmTypVal.VAL "VAL"
from
  ATS_TRNSCTN_LOG log
  LEFT OUTER JOIN BKNG_ACMDTN bkngAccmd ON log.BKNG_ID = bkngAccmd.BKNG_ID
  JOIN ACMDTN_TYP acmTyp on bkngAccmd.acmdtn_typ_cde = acmTyp.acmdtn_typ_cde
  LEFT OUTER JOIN ACMDTN_TYP_VAL acmTypVal on bkngAccmd.acmdtn_typ_val_id_no = acmTypVal.acmdtn_typ_val_id_no