package model;

import java.time.LocalDateTime;

/**
 * The class below creates the "User" objects.
 * @author Alexander Cowan
 */
public class User {
        private int user_Id;
        private String userName;
        private String password;
        private LocalDateTime create_Date;
        private String created_By;
        private LocalDateTime last_Update;
        private String lastUpdateBy;

        public User(int userId, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
            this.user_Id = userId;
            this.userName = userName;
            this.password = password;
            this.create_Date = createDate;
            this.created_By = createdBy;
            this.last_Update = lastUpdate;
            this.lastUpdateBy = lastUpdateBy;
        }

        /**
         * @return the userId
         */
        public int getUserId() {
            return user_Id;
        }

        /**
         * @param userId the userId to set
         */
        public void setUserId(int userId) {
            this.user_Id = userId;
        }

        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

        /**
         * @param userName the userName to set
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * @return the createDate
         */
        public LocalDateTime getCreateDate() {
            return create_Date;
        }

        /**
         * @param createDate the createDate to set
         */
        public void setCreateDate(LocalDateTime createDate) {
            this.create_Date = createDate;
        }

        /**
         * @return the createdBy
         */
        public String getCreatedBy() {
            return created_By;
        }

        /**
         * @param createdBy the createdBy to set
         */
        public void setCreatedBy(String createdBy) {
            this.created_By = createdBy;
        }

        /**
         * @return the lastUpdate
         */
        public LocalDateTime getLastUpdate() {
            return last_Update;
        }

        /**
         * @param lastUpdate the lastUpdate to set
         */
        public void setLastUpdate(LocalDateTime lastUpdate) {
            this.last_Update = lastUpdate;
        }

        /**
         * @return the lastUpdateBy
         */
        public String getLastUpdateBy() {
            return lastUpdateBy;
        }

        /**
         * @param lastUpdateBy the lastUpdateBy to set
         */
        public void setLastUpdateBy(String lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        /**
         * @return the User String name
         */
        @Override
        public String toString(){return userName;}
    }