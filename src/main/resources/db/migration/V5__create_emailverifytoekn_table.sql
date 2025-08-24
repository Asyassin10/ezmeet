CREATE TABLE email_verification_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT UNIQUE, -- OneToOne => لازم يكون فريد
    expiry_date TIMESTAMP NOT NULL,

    CONSTRAINT fk_user_verification FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
