export default class StatsUser {
  id!: number;
  name!: string;
  username!: string;
  role!: string;
  coursesNumber: number = 0;

  constructor(jsonObj?: StatsUser) {
    if (jsonObj) {
      this.id = jsonObj.id;
      this.name = jsonObj.name;
      this.username = jsonObj.username;
      this.role = jsonObj.role;
    }
  }
}