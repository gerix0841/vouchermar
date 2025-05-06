INSERT INTO voucher (id, code, max_redemptions, expiry_date, current_redemptions, active)
VALUES
(101, '000000001', 1, NULL, 0, true),
(102, '000000002', 5, NULL, 0, true),
(103, '000000003', 1, DATEADD('YEAR', 1, CURRENT_DATE), 0, true);