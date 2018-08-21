
CREATE SCHEMA "Banking_App";

CREATE TABLE users(username VARCHAR(25) UNIQUE NOT NULL, password VARCHAR(25) NOT NULL,
 full_name VARCHAR(40), age INTEGER, PRIMARY KEY(USERNAME));

CREATE TABLE accounts(account_id SERIAL NOT NULL PRIMARY KEY, balance NUMERIC(10,2),type VARCHAR(15));

CREATE TABLE user_account_junction(username VARCHAR(25), account_id INT, 
	PRIMARY KEY(username, account_id),FOREIGN KEY (username) REFERENCES users(username),
	 FOREIGN KEY (account_id) REFERENCES accounts(account_id));

CREATE TABLE transaction_history(transaction_id SERIAL IDENTITY  PRIMARY KEY, account_id INT, transaction_num INT, transaction_amount NUMERIC(10,2),
	FOREIGN KEY (account_id) REFERENCES accounts(account_id));

DECLARE difference NUMERIC(10,2);
DECLARE trans_num INT;
BEGIN 
difference := NEW.balance- OLD.balance;
trans_num := 0;
IF  EXISTS(SELECT 1 FROM transaction_history
	WHERE transaction_history.account_id = new.account_id) THEN
	trans_num := MAX(transaction_num) FROM transaction_history
	WHERE transaction_history.account_id = new.account_id;
	trans_num := trans_num + 1;
END IF;
INSERT INTO transaction_history 
	(account_id, transaction_num,transaction_amount)
	VALUES (new.account_id, trans_num,difference);
RETURN NEW;
END;
	

CREATE trigger log_trans
BEFORE UPDATE ON accounts
FOR EACH ROW
EXECUTE PROCEDURE log_trans();
