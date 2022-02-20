INSERT INTO mhlee.t_lang (f_lang, f_name) VALUES ('KO', '한국어');
INSERT INTO mhlee.t_lang (f_lang, f_name) VALUES ('EN', '영어');

INSERT INTO mhlee.t_user (f_user_id, f_user_email, f_user_nm, f_user_pw, f_use_yn) VALUES ('admin', 'noreply@this.site', '관리자', 'qwer1234', 'Y');
INSERT INTO mhlee.t_user (f_user_id, f_user_email, f_user_nm, f_user_pw, f_use_yn) VALUES ('mhlee', 'mhlee@in-soft.co.kr', '이민호', 'qwer1234', 'Y');

INSERT INTO mhlee.t_project (f_proj_no, f_proj_nm, user_entity_f_user_id, f_use_yn) VALUES (0, 'ZMON', 'mhlee', 'Y');

INSERT INTO mhlee.t_role (f_role_no, f_role_nm, user_entity_f_user_id) VALUES (0, 'ADMIN', 'admin');
INSERT INTO mhlee.t_role (f_role_no, f_role_nm, user_entity_f_user_id) VALUES (1, 'USER', 'mhlee');

INSERT INTO mhlee.t_code (f_gid, f_cid, f_cname, f_use_yn) VALUES ('000', '000', '코드명', 'Y');

INSERT INTO mhlee.t_company (f_no, f_name, f_memo, f_use_yn) VALUES (0, 'INSoft', '아이엔소프트', 'Y');
INSERT INTO mhlee.t_company (f_no, f_name, f_memo, f_use_yn) VALUES (1, 'SK C&C', 'SK C&C', 'Y');

select * from t_company tc ;

INSERT INTO mhlee.t_customer (f_no, f_email, f_name, f_phone_no, compony_entity_f_no, f_use_yn) VALUES (0, 'mhlee@in-soft.co.kr', '이민호', '010-1234-5678', 0, 'Y');

INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (0, '문제');
INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (1, '개선');
INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (2, '변경');
INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (3, '요구');
INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (4, '제안');
INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (5, '문의');

INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(0, '대기');
INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(1, '접수');
INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(2, '검토');
INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(3, '완료');
INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(4, '보류');
INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(5, '기각');