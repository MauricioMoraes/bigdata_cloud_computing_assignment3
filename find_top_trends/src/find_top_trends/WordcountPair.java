package find_top_trends;


public class WordcountPair implements Comparable<WordcountPair> {
    public String mWord;
    public long mCount;

    public WordcountPair(String word, long count) {
        this.mWord = word;
        this.mCount = count;
    }

    @Override
    public int compareTo(WordcountPair o) {
        if (this.mCount > o.mCount)
            return 1;
        else if (this.mCount < o.mCount)
            return -1;
        return 0;
    }
}