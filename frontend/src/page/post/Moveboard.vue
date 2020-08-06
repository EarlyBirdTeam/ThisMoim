<template>
  <div id="app">
    <div class="flex justify-center">
      <div class="min-h-screen d-flex overflow-x-scroll py-12">
        <div
          v-for="column in columns"
          :key="column.title"
          class="bg-gray-100 rounded-lg px-3 py-3 column-width rounded mr-4"
        >
          <p class="text-gray-700 font-semibold font-sans tracking-wide text-sm">{{column.title}}</p>
          <!-- Draggable component comes from vuedraggable. It provides drag & drop functionality -->
          <draggable
            :list="column.tasks"
            :animation="200"
            style="margin-right:0"
            ghost-class="ghost-card"
            group="tasks"
          >
            <!-- Each element from here will be draggable and animated. Note :key is very important here to be unique both for draggable and animations to be smooth & consistent. -->

            <div v-for="(task,idx) in column.tasks" :key="idx" class="mt-3 cursor-move">
              <div class="bg-white shadow rounded px-3 pt-3 pb-5 border border-white">
                <div class="d-flex justify-space-between">
                  <p
                    class="text-truncate text-gray-700 font-semibold font-sans text-sm word-break:keep-all;"
                    style="word-break:keep-all; "
                  >{{task}}</p>

                  <div>
                    <v-icon>mdi-pencil</v-icon>
                    <v-icon v-on:click="deleteTask(column.title,task)">mdi-delete</v-icon>
                  </div>
                </div>
              </div>
              <!-- <input v-model="column.tasks[idx]"/> -->
            </div>

            <!-- </transition-group> -->
          </draggable>
          <div v-on:click="addTask(column.title)">+</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";

export default {
  name: "App",
  components: {
    draggable,
  },
  data() {
    return {
      columns: [
        {
          title: "할 일",
          tasks: [],
        },
        {
          title: "진행중",
          tasks: [],
        },
        {
          title: "완료",
          tasks: [],
        },
      ],
    };
  },
  methods: {
    addTask(title, idx) {
      const newTask = prompt(title, "");
      this.columns.find((column) => column.title === title).tasks.push(newTask);
    },
    deleteTask(title, task) {
      console.log(title);
      console.log(task);
      var index = this.columns
        .find((column) => column.title === title)
        .tasks.indexOf(task);

      // var index = tasks.indexOf(task);
      console.log(index);
      this.columns
        .find((column) => column.title === title)
        .tasks.splice(index, 1);
    },
  },
};
</script>

<style scoped>
.column-width {
  min-width: 320px;
  width: 320px;
  margin-right: 0;
}
/* Unfortunately @apply cannot be setup in codesandbox, 
but you'd use "@apply border opacity-50 border-blue-500 bg-gray-200" here */
.ghost-card {
  opacity: 0.5;
  background: #f7fafc;
  border: 1px solid #4299e1;
}
</style>
