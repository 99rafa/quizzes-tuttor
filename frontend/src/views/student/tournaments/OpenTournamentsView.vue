<template>
  <div class="container">
    <h2>Open Tournaments</h2>
    <ul>
      <li class="list-header">
        <div class="col">Title</div>
        <div class="col">Available since</div>
        <div class="col">Available until</div>
        <div class="col last-col"></div>
      </li>
      <li
        class="list-row"
        v-for="tournament in tournaments"
        :key="tournament.id"
        @click="solveQuiz(tournament.id)"
      >
        <div class="col">
          {{ tournament.title }}
        </div>
        <div class="col">
          {{ tournament.availableDate }}
        </div>
        <div class="col">
          {{ tournament.conclusionDate }}
        </div>
        <div class="col last-col">
          <i class="fas fa-chevron-circle-right" data-cy="startQuiz"></i>
        </div>
      </li>
    </ul>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Tournament from '@/models/management/Tournament';
import RemoteServices from '@/services/RemoteServices';
import StatementQuiz from '@/models/statement/StatementQuiz';
import StatementManager from '@/models/statement/StatementManager';

@Component
export default class OpenTournamentsView extends Vue {
  tournaments: Tournament[] = [];

  async created() {
    await this.$store.dispatch('loading');
    try {
      this.tournaments = (await RemoteServices.listOpenTournaments()).reverse();
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  async solveQuiz(tournamentId: number) {
    try {
      let statementManager: StatementManager = StatementManager.getInstance;
      statementManager.statementQuiz = await RemoteServices.getTournamentQuiz(
        tournamentId
      );
      await this.$router.push({ name: 'solve-quiz' });
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
  padding-left: 10px;
  padding-right: 10px;

  h2 {
    font-size: 26px;
    margin: 20px 0;
    text-align: center;
    small {
      font-size: 0.5em;
    }
  }

  ul {
    overflow: hidden;
    padding: 0 5px;

    li {
      border-radius: 3px;
      padding: 15px 10px;
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }

    .list-header {
      background-color: #1976d2;
      color: white;
      font-size: 14px;
      text-transform: uppercase;
      letter-spacing: 0.03em;
      text-align: center;
    }

    .col {
      flex-basis: 25% !important;
      margin: auto; /* Important */
      text-align: center;
    }

    .list-row {
      background-color: #ffffff;
      box-shadow: 0 0 9px 0 rgba(0, 0, 0, 0.1);
      display: flex;
    }
  }
}
</style>
