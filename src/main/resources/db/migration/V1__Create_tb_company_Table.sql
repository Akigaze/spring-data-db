
CREATE TABLE tb_company (
  id int AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  created_date TIMESTAMP DEFAULT NOW()
);
