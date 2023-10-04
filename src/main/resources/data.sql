-- Điều này là một ví dụ về cách thực hiện nó bằng SQL.
-- Cần phải thay đổi các giá trị name, price và description trong các câu lệnh INSERT INTO này.

DECLARE @counter INT = 0;

WHILE @counter < 100
BEGIN
    INSERT INTO item (name, price, description)
    VALUES ('Tên Sản phẩm ' + CAST(@counter AS NVARCHAR), 10.99, 'Mô tả sản phẩm ' + CAST(@counter AS NVARCHAR));

    SET @counter = @counter + 1;
END;
