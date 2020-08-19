// const { sequelize, Sequelize } = require(".");

// Sequelize : DB에서 조회 된 값을 객체로 전달해주는 ORM(object-relational mapping)

module.exports = (sequelize, Sequelize) => {
    const Text = sequelize.define("text", {
        message: {
            type: Sequelize.STRING
        },
        type:{
            type: Sequelize.BIGINT
        },
        T:{
            type: Sequelize.BIGINT
        },
        L:{
            type: Sequelize.BIGINT
        }
    });

    return Text;
};