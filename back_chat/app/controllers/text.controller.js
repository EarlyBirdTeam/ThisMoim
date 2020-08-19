const db = require("../models");
const Text = db.texts;
const Op = db.Sequelize.Op;

exports.create = (req, res) =>{
    if(!req.body.message){
        res.status(400).send({
            message: "Title can not be empty!"
        });
        return;
    }

    // Create a Tutorial
    const text = {
        message: req.body.message,
        type: req.body.type,
        T: req.body.T,
        L: req.body.L,
    }

    Text.create(text).
        then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occurred while creating the tutorial."
            });
        });
};

exports.findAll = (req, res) =>{
    console.log("text findAll 실행");

    const message = req.query.message;
    var condition = message ? { message: { [Op.like]: `%${message}%` } } : null;

    Text.findAll({ where: condition })
        .then(data => {
        res.send(data);
        })
        .catch(err => {
        res.status(500).send({
            message:
            err.message || "Some error occurred while retrieving tutorials."
        });
        });
};

// exports.findOne = (req, res) =>{
//     const id = req.params.id;

//     Tutorial.findByPk(id)
//         .then(data => {
//         res.send(data);
//         })
//         .catch(err => {
//         res.status(500).send({
//             message: "Error retrieving Tutorial with id=" + id
//         });
//         });
// };

exports.update = (req, res) =>{
    const id = req.params.id;

    Text.update(req.body, {
        where: { id: id }
    })
        .then(num => {
        if (num == 1) {
            res.send({
            message: "Tutorial was updated successfully."
            });
        } else {
            res.send({
            message: `Cannot update Text with id=${id}. Maybe Tutorial was not found or req.body is empty!`
            });
        }
        })
        .catch(err => {
        res.status(500).send({
            message: "Error updating Tutorial with id=" + id
        });
        });
};

// exports.delete = (req, res) =>{
//     const id = req.params.id;

//     Tutorial.destroy({
//         where: { id: id }
//     })
//         .then(num => {
//         if (num == 1) {
//             res.send({
//             message: "Tutorial was deleted successfully!"
//             });
//         } else {
//             res.send({
//             message: `Cannot delete Tutorial with id=${id}. Maybe Tutorial was not found!`
//             });
//         }
//         })
//         .catch(err => {
//         res.status(500).send({
//             message: "Could not delete Tutorial with id=" + id
//         });
//         });
// };

// exports.deleteAll = (req, res) =>{
//     Tutorial.destroy({
//         where: {},
//         truncate: false
//       })
//         .then(nums => {
//           res.send({ message: `${nums} Tutorials were deleted successfully!` });
//         })
//         .catch(err => {
//           res.status(500).send({
//             message:
//               err.message || "Some error occurred while removing all tutorials."
//           });
//         });
// };

// exports.findAllPublished = (req, res) =>{
//     Tutorial.findAll({ where: { published: true } })
//     .then(data => {
//       res.send(data);
//     })
//     .catch(err => {
//       res.status(500).send({
//         message:
//           err.message || "Some error occurred while retrieving tutorials."
//       });
//     });
// };