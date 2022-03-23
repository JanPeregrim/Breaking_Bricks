package gamestudio.service;
import gamestudio.entity.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreServiceFile implements ScoreService {
    private static final String FILE = "score.bin";

    private List<Score> scores = new ArrayList<>();

    @Override
    public void addScore(Score score) {
        scores.add(score);
        save();
    }

    @Override
    public List<Score> getTopScores(String game) {
        scores = load();
        return scores.stream()
                .filter(s -> s.getGame().equals(game))
                .sorted((s1, s2) -> -Integer.compare(s1.getPoints(), s2.getPoints()))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        scores = new ArrayList<>();
        save();
    }

    private List<Score> load() {
        try (var is = new ObjectInputStream(new FileInputStream(FILE))) {
            return (List<Score>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new GameStudioException(e);
        }
    }

    private void save() {
        try (var os = new ObjectOutputStream(new FileOutputStream(FILE))) {
            os.writeObject(scores);
        } catch (IOException e) {
            throw new GameStudioException(e);
        }
    }
}
