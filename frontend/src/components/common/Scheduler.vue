<template>
  <div class="scheduler MoveableBox" >
    <JqxScheduler
      ref="myScheduler"
      @appointmentChange="onAppointmentChange($event)"
      :width="800"
      :height="600"
      :source="dataAdapter"
      :date="date"
      :showLegend="true"
      :view="'weekView'"
      :appointmentDataFields="appointmentDataFields"
      :resources="resources"
      :views="views"
    />
  </div>
</template>

<script>
import JqxScheduler from "jqwidgets-scripts/jqwidgets-vue/vue_jqxscheduler.vue";

export default {
  components: {
    JqxScheduler
  },
  data() {
    return {
      date: new jqx.date(2016, 11, 23),
      appointmentDataFields: {
        from: "start",
        to: "end",
        id: "id",
        description: "description",
        location: "location",
        subject: "subject",
        resourceId: "calendar"
      },
      resources: {
        colorScheme: "scheme05",
        dataField: "calendar",
        source: new jqx.dataAdapter(this.source)
      },
      views: ["dayView", "weekView", "monthView"]
    }
  },
  beforeCreate() {
    const generateAppointments = function() {
      const appointments = [{"start":"2016-11-27T11:00:00","end":"2016-11-27T13:00:00","id":"id1","description":"","place":"","subject":"Course Social Media","calendar":"Room 3"},{"start":"2016-11-21T13:30:00","end":"2016-11-21T14:00:00","id":"2831-18-20-31-28","description":"","place":"","subject":"7","calendar":"Room 1"},{"start":"2016-11-21T13:30:00","end":"2016-11-21T14:00:00","id":"3118-29-27-30-23","description":"","place":"","subject":"7","calendar":"Room 1"},{"start":"2016-11-21T13:30:00","end":"2016-11-21T14:00:00","id":"3027-19-23-17-17","description":"","place":"","subject":"7","calendar":"Room 1"},{"start":"2016-11-21T13:30:00","end":"2016-11-21T14:00:00","id":"1725-20-29-27-23","description":"","place":"","subject":"7","calendar":"Room 1"},{"start":"2016-11-22T09:30:00","end":"2016-11-22T10:00:00","id":"3021-29-21-20-22","description":"","place":"777","subject":"7","calendar":"Room 1"},{"start":"2016-11-22T09:30:00","end":"2016-11-22T10:00:00","id":"1725-21-20-20-24","description":"7","place":"777","subject":"7","calendar":"Room 1"},{"start":"2016-11-22T09:30:00","end":"2016-11-22T10:00:00","id":"2519-20-29-23-28","description":"7","place":"777","subject":"7","calendar":"Room 1"}]
      return appointments;
    };
    this.source = {
      dataType: "array",
      dataFields: [
        { name: "id", type: "string" },
        { name: "description", type: "string" },
        { name: "location", type: "string" },
        { name: "subject", type: "string" },
        { name: "calendar", type: "string" },
        { name: "start", type: "date" },
        { name: "end", type: "date" }
      ],
      id: "id",
      localData: generateAppointments(),
    };
    this.dataAdapter = new jqx.dataAdapter(this.source);
  },
    mounted: function() {
    this.$refs.myScheduler.ensureAppointmentVisible("id1");
    let appointment1 = {
      id: "id1",
      description: "",
      subject: "New appointment",
      calendar: "Room 1",
      start: new Date(2016, 10, 23, 9, 0, 0),
      end: new Date(2016, 10, 23, 11, 0, 0)
    };
    this.$refs.myScheduler.addAppointment(appointment1);
  },
  methods: {
    debug() {
      console.log(this.source.localData);
    },
    onAppointmentChange: function (event) {
        // console.log(event);
        // console.log(this.$refs.myScheduler)
        this.$refs.myScheduler.addAppointment(event);
    }
  },
}
</script>

<style scoped>
.scheduler {
  width: 830px;
  height: 630px;
  padding: 1%;
}
</style>