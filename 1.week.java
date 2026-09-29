import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

class Team {
    String name;
    int played;
    int wins;
    int draws;
    int losses;
    int goalsFor;
    int goalsAgainst;
    int goalDifference;
    int points;

    public Team(String name) {
        this.name = name;
        this.played = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.goalDifference = 0;
        this.points = 0;
    }
}

class Match {
    int team1Index;
    int team2Index;
    int score1;
    int score2;

    public Match(int team1Index, int team2Index) {
        this.team1Index = team1Index;
        this.team2Index = team2Index;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 4 takim tanimlama
        Team[] teams = {
            new Team("Team A"),
            new Team("Team B"),
            new Team("Team C"),
            new Team("Team D")
        };

        // 6 maclik sabit fikstur
        Match[] fixture = {
            new Match(0, 1), // Team A vs Team B
            new Match(2, 3), // Team C vs Team D
            new Match(0, 2), // Team A vs Team C
            new Match(1, 3), // Team B vs Team D
            new Match(0, 3), // Team A vs Team D
            new Match(1, 2)  // Team B vs Team C
        };

        // 1. Fixture & Score Input
        System.out.println("--- TOURNAMENT FIXTURE ---");
        for (int i = 0; i < fixture.length; i++) {
            String t1 = teams[fixture[i].team1Index].name;
            String t2 = teams[fixture[i].team2Index].name;
            System.out.println("Match " + (i + 1) + ": " + t1 + " vs " + t2);
        }
        System.out.println("---------------------------\n");

        // Skorlarin alinmasi ve istatistiklerin islenmesi
        for (int i = 0; i < fixture.length; i++) {
            Team t1 = teams[fixture[i].team1Index];
            Team t2 = teams[fixture[i].team2Index];

            System.out.println("Match " + (i + 1) + ": " + t1.name + " vs " + t2.name);
            System.out.print(t1.name + " goals: ");
            fixture[i].score1 = scanner.nextInt();
            System.out.print(t2.name + " goals: ");
            fixture[i].score2 = scanner.nextInt();

            // Oynanan mac sayilari
            t1.played++;
            t2.played++;

            // Gol istatistikleri
            t1.goalsFor += fixture[i].score1;
            t1.goalsAgainst += fixture[i].score2;
            t2.goalsFor += fixture[i].score2;
            t2.goalsAgainst += fixture[i].score1;

            // 2. Points Calculation
            if (fixture[i].score1 > fixture[i].score2) {
                t1.wins++;
                t1.points += 3;
                t2.losses++;
            } else if (fixture[i].score2 > fixture[i].score1) {
                t2.wins++;
                t2.points += 3;
                t1.losses++;
            } else {
                t1.draws++;
                t1.points += 1;
                t2.draws++;
                t2.points += 1;
            }
            System.out.println();
        }

        // Girilen mac skorlarini ekrana yazdirma
        System.out.println("--- MATCH RESULTS ---");
        for (int i = 0; i < fixture.length; i++) {
            String t1 = teams[fixture[i].team1Index].name;
            String t2 = teams[fixture[i].team2Index].name;
            System.out.println("Match " + (i + 1) + ": " + t1 + " " + fixture[i].score1 + " - " + fixture[i].score2 + " " + t2);
        }
        System.out.println("---------------------\n");

        // 3. Goal Difference Calculation
        for (Team team : teams) {
            team.goalDifference = team.goalsFor - team.goalsAgainst;
        }

        // 4. Standings Table & Siralama
        // Puana gore, esitlik halinde averaja (goal difference) gore azalan siralama
        Arrays.sort(teams, new Comparator<Team>() {
            @Override
            public int compare(Team a, Team b) {
                if (b.points != a.points) {
                    return b.points - a.points;
                }
                return b.goalDifference - a.goalDifference;
            }
        });

        // Tabloyu ekrana formatli yazdirma
        System.out.println("==================================================");
        System.out.printf("%-10s | %-3s | %-3s | %-3s | %-3s | %-4s | %-3s\n", "Team", "P", "W", "D", "L", "GD", "PTS");
        System.out.println("--------------------------------------------------");
        for (Team team : teams) {
            System.out.printf("%-10s | %-3d | %-3d | %-3d | %-3d | %-4d | %-3d\n",
                    team.name, team.played, team.wins, team.draws, team.losses, team.goalDifference, team.points);
        }
        System.out.println("==================================================\n");

        // 5. Champion Announcement
        Team champion = teams[0];
        System.out.println("Tournament Champion: " + champion.name);

        scanner.close();
    }
}