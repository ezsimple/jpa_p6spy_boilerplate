INSERT INTO mhlee.t_lang (f_lang, f_name) VALUES ('KO', '한국어');
INSERT INTO mhlee.t_lang (f_lang, f_name) VALUES ('EN', '영어');

INSERT INTO mhlee.t_user (f_user_id, f_user_email, f_user_nm, f_user_pw, f_use_yn) VALUES ('admin', 'noreply@this.site', '관리자', 'qwer1234', 'Y');
INSERT INTO mhlee.t_user (f_user_id, f_user_email, f_user_nm, f_user_pw, f_use_yn) VALUES ('mhlee', 'mhlee@in-soft.co.kr', '이민호', 'qwer1234', 'Y');

INSERT INTO mhlee.t_project (f_proj_no, f_proj_nm, f_user_id, f_use_yn) VALUES (0, 'ZMON', 'mhlee', 'Y');

INSERT INTO mhlee.t_role (f_role_no, f_role_nm, f_user_id) VALUES (0, 'ADMIN', 'admin');
INSERT INTO mhlee.t_role (f_role_no, f_role_nm, f_user_id) VALUES (1, 'USER', 'mhlee');

INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('000', '종류', '000', '000000', '문제', 'Y', 0);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('000', '종류', '001', '000001', '개선', 'Y', 1);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('000', '종류', '002', '000002', '변경', 'Y', 2);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('000', '종류', '003', '000003', '요구', 'Y', 3);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('000', '종류', '004', '000004', '제안', 'Y', 4);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('000', '종류', '005', '000005', '문의', 'Y', 5);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('001', '진행상황', '000', '001000', '대기', 'Y', 0);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('001', '진행상황', '001', '001001', '접수', 'Y', 1);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('001', '진행상황', '002', '001002', '검토', 'Y', 2);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('001', '진행상황', '003', '001003', '완료', 'Y', 3);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('001', '진행상황', '004', '001004', '보류', 'Y', 4);
INSERT INTO mhlee.t_code (f_gid, f_gname, f_cid, f_cname, f_code6, f_use_yn, f_order_no) VALUES ('001', '진행상황', '005', '001005', '기각', 'Y', 5);

INSERT INTO mhlee.t_company (f_no, f_name, f_memo, f_use_yn) VALUES (0, 'INSoft', '아이엔소프트', 'Y');
INSERT INTO mhlee.t_company (f_no, f_name, f_memo, f_use_yn) VALUES (1, 'SK C&C', 'SK C&C', 'Y');

INSERT INTO mhlee.t_customer (f_no, f_email, f_name, f_phone_no, f_company_no, f_use_yn) VALUES (0, 'mhlee@in-soft.co.kr', '이민호', '010-1234-5678', 0, 'Y');

-- INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (0, '문제');
-- INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (1, '개선');
-- INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (2, '변경');
-- INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (3, '요구');
-- INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (4, '제안');
-- INSERT INTO mhlee.t_kind (f_kind_no, f_kind_nm) VALUES (5, '문의');
--
-- INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(0, '대기');
-- INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(1, '접수');
-- INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(2, '검토');
-- INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(3, '완료');
-- INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(4, '보류');
-- INSERT INTO mhlee.t_progress (f_sts_no, f_sts_nm) VALUES(5, '기각');