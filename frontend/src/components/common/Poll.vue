<template>

<div>
    <div>
    {{test}}
    </div>
        <div>
            New poll
        </div>
        <div>
            <div>
                <input v-model="poll.question" type="text" placeholder="Your Question...">
            </div>
            <div>
                <div v-for="(answer, index) in poll.answers" :key="index" class="answer" :style="{zIndex: poll.answers.length - index}">
                    <input :placeholder="'Answer ' + (index + 1)" @focus="createNewInput(index)" v-model="poll.answers[index].answer" type="text">
                    <span class="delete" @click="deleteInput(index)">delete</span>
                </div>
            </div>
            <div>
                <button @click="save">save</button>
            </div>

        </div>

    </div>
</template>

<script>
export default {
    data() {
        return {
            poll: {
                question: "",
                answers: [
                    { answer: "" }
                ],
                multipleVotes: false
            },
            test:[],

        };
    },
    methods: {
        createNewInput(index) {
            if (this.poll.answers.length - 1 == index) {
                this.poll.answers.push({ answer: "" });
            }
        },
        deleteInput(index) {
            if (index > 0 || this.poll.answers.length > 1) {
                this.poll.answers.splice(index, 1);
            }
        },
        save: function() {
            this.test= this.poll
        }  
    }
}
</script>

<style>

</style>