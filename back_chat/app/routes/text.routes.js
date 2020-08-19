module.exports = app => {
    const texts = require("../controllers/text.controller.js");

    var router = require("express").Router();

    // Create a new Tutorial
    router.post("/", texts.create);

    // Retrieve all Tutorials
    router.get("/", texts.findAll);

    //  // Retrieve all published Tutorials
    // router.get("/published", tutorials.findAllPublished);

    // // Retrieve a single Tutorial with id
    // router.get("/:id", tutorials.findOne);

    // Update a Tutorial with id
    router.put("/:id", texts.update);

    // // Delete a Tutorial with id
    // router.delete("/:id", tutorials.delete);

    // // Delete all Tutorials
    // router.delete("/", tutorials.deleteAll);

    app.use('/api/texts', router);
    // //app.use('/test', router);
};