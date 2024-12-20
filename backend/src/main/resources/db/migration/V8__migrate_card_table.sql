ALTER TABLE card RENAME COLUMN cardNumber TO card_number;
ALTER TABLE card ALTER COLUMN card_number TYPE VARCHAR(255);
ALTER TABLE card RENAME COLUMN expirationDate TO expiration_date;
ALTER TABLE card RENAME COLUMN creditLimit TO credit_limit;
ALTER TABLE card RENAME COLUMN cardBalance TO card_balance;
ALTER TABLE card RENAME COLUMN minimumPayment TO minimum_payment;
ALTER TABLE card RENAME COLUMN rewardPoints TO reward_points;
ALTER TABLE card RENAME COLUMN recommendedCreditLimit TO recommended_credit_limit;
ALTER TABLE card RENAME COLUMN verificationReason TO verification_reason;
ALTER TABLE card RENAME COLUMN cardStatus TO card_status;
ALTER TABLE card RENAME COLUMN isApproved TO is_approved;