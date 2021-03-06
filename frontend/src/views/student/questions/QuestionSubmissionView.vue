<template>
  <v-card class="table">
    <v-data-table
      :headers="headers"
      :custom-filter="customFilter"
      :items="submissions"
      :search="search"
      multi-sort
      :mobile-breakpoint="0"
      :items-per-page="15"
      :footer-props="{ itemsPerPageOptions: [15, 30, 50, 100] }"
    >
      <template v-slot:top>
        <v-card-title>
          <v-text-field
            v-model="search"
            append-icon="search"
            label="Search"
            class="mx-2"
          />

          <v-spacer />
          <v-btn
            color="primary"
            dark
            @click="newSubmission"
            data-cy="createSubmissionButton"
            >New Question</v-btn
          >
        </v-card-title>
      </template>

      <template v-slot:item.status="{ item }">
        <v-chip v-if="item.status" :color="getStatusColor(item.status)" dark>{{
          item.status
        }}</v-chip>
      </template>

      <template v-slot:item.topics="{ item }">
        <edit-submission-topics
          :submission="item"
          :topics="topics"
          v-on:submission-changed-topics="onSubmissionChangedTopics"
        />
      </template>

      <template v-slot:item.image="{ item }">
        <v-file-input
          show-size
          dense
          small-chips
          @change="handleFileUpload($event, item)"
          accept="image/*"
        />
      </template>
      <template v-slot:item.action="{ item }">
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-icon
              small
              class="mr-2"
              v-on="on"
              @click="showSubmissionDialog(item)"
              data-cy="showSubmission"
              >visibility</v-icon
            >
          </template>
          <span>Show Submission</span>
        </v-tooltip>
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-icon
              small
              class="mr-2"
              v-on="on"
              @click="editSubmission(item)"
              data-cy="reSubmitSubmission"
              >fas fa-redo</v-icon
            >
          </template>
          <span>Re-Submit Question</span>
        </v-tooltip>
      </template>
    </v-data-table>

    <edit-submission-dialog
      v-if="currentSubmission"
      v-model="editSubmissionDialog"
      :submission="currentSubmission"
      v-on:save-submission="onSaveSubmission"
    />
    <show-submission-dialog
      v-if="currentSubmission"
      v-model="submissionDialog"
      :submission="currentSubmission"
      v-on:close-show-submission-dialog="onCloseShowSubmissionDialog"
    />
  </v-card>
</template>
<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import RemoteServices from '@/services/RemoteServices';
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Submission from '@/models/management/Submission';
import Question from '@/models/management/Question';
import Topic from '@/models/management/Topic';
import Image from '@/models/management/Image';

import EditSubmissionTopics from '@/views/submission/EditSubmissionTopics.vue';
import ShowSubmissionDialog from '@/views/submission/ShowSubmissionDialog.vue';
import EditSubmissionDialog from '@/views/student/questions/EditSubmissionDialog.vue';

@Component({
  components: {
    'show-submission-dialog': ShowSubmissionDialog,
    'edit-submission-dialog': EditSubmissionDialog,
    'edit-submission-topics': EditSubmissionTopics

  }
})
export default class QuestionSubmissionView extends Vue {
  submissions: Submission[] = [];
  topics: Topic[] = [];
  currentSubmission: Submission | null = null;
  search: string = '';
  submissionDialog: boolean = false;
  editSubmissionDialog: boolean = false;

  headers: object = [
    { text: 'Title', value: 'title', align: 'center' },
    { text: 'Question', value: 'content', align: 'left' },
    {
      text: 'Topics',
      value: 'topics',
      align: 'center',
      sortable: false
    },
    {
      text: 'Justification',
      value: 'justification',
      align: 'center'
    },
    { text: 'Status', value: 'status', align: 'center' },

    {
      text: 'Creation Date',
      value: 'creationDate',
      align: 'center'
    },
    {
      text: 'Image',
      value: 'image',
      align: 'center',
      sortable: false
    },
    {
      text: 'Actions',
      value: 'action',
      align: 'center',
      sortable: false
    }
  ];

  @Watch('editSubmissionDialog')
  closeError() {
    if (!this.editSubmissionDialog) {
      this.currentSubmission = null;
    }
  }

  async created() {
    await this.$store.dispatch('loading');
    try {
      [this.topics, this.submissions] = await Promise.all([
        RemoteServices.getTopics(),
        RemoteServices.getStudentSubmissions()
      ]);
      this.customSorter();
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  customFilter(value: string, search: string, submission: Submission) {
    // noinspection SuspiciousTypeOfGuard,SuspiciousTypeOfGuard
    return (
      search != null &&
      JSON.stringify(submission)
        .toLowerCase()
        .indexOf(search.toLowerCase()) !== -1
    );
  }

  onSubmissionChangedTopics(submissionId: Number, changedTopics: Topic[]) {
    let submission = this.submissions.find(
      (submission: Submission) => submission.id == submissionId
    );
    if (submission) {
      submission.topics = changedTopics;
    }
  }

  convertMarkDownNoFigure(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }

  async handleFileUpload(event: File, submission: Submission) {
    if (submission.id) {
      try {
        const imageURL = await RemoteServices.uploadSubmissionImage(
          event,
          submission.id
        );
        submission.image = new Image();
        submission.image.url = imageURL;
        submission.imageUrl = imageURL;
        confirm('Image ' + imageURL + ' was uploaded!');
      } catch (error) {
        await this.$store.dispatch('error', error);
      }
    }
  }

  onCloseShowSubmissionDialog() {
    this.currentSubmission = null;
    this.submissionDialog = false;
  }

  showSubmissionDialog(submission: Submission) {
    this.currentSubmission = submission;
    this.submissionDialog = true;
  }

  getStatusColor(status: string) {
    if (status === 'REJECTED') return 'red';
    else if (status === 'ONHOLD') return 'orange';
    else return 'green';
  }

  editSubmission(submission: Submission, e?: Event) {
    if (e) e.preventDefault();
    this.currentSubmission = submission;
    this.editSubmissionDialog = true;
  }

  newSubmission() {
    this.currentSubmission = new Submission();
    this.editSubmissionDialog = true;
  }

  async onSaveSubmission(submission: Submission) {
    this.submissions = this.submissions.filter(q => q.id !== submission.id);
    this.submissions.unshift(submission);
    this.editSubmissionDialog = false;
    this.currentSubmission = null;
  }

  customSorter() {
    let aux, a, b;
    for (let i = 0; i < this.submissions.length - 1; i++) {
      for (let j = i + 1; j < this.submissions.length; j++) {
        a = this.submissions[i];
        b = this.submissions[j];
        if (a.status === 'REJECTED') {
          if (b.status === 'ONHOLD') {
            aux = this.submissions[i];
            this.submissions[i] = b;
            this.submissions[j] = aux;
          } else if (b.status == 'APPROVED') {
            aux = this.submissions[i];
            this.submissions[i] = b;
            this.submissions[j] = aux;
          }
        } else if (a.status == 'APPROVED') {
          if (b.status === 'ONHOLD') {
            aux = this.submissions[i];
            this.submissions[i] = b;
            this.submissions[j] = aux;
          }
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.question-textarea {
  text-align: left;

  .CodeMirror,
  .CodeMirror-scroll {
    min-height: 200px !important;
  }
}
.option-textarea {
  text-align: left;

  .CodeMirror,
  .CodeMirror-scroll {
    min-height: 100px !important;
  }
}
</style>
