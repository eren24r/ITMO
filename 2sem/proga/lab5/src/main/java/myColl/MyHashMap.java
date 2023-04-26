package myColl;
@SuppressWarnings("unchecked")
public class MyHashMap<K, V> {
    private int size = 16;
    private Listelement<K, V>[] table = new Listelement[16];
    private int rl_size = 0;

    public boolean put(K k, V v) {
        int ind = size - 1 & k.hashCode();
        if (table[ind] == null) {
            table[ind] = new Listelement(k.hashCode(), k, v, null);
            return true;
        } else {
            if (k.equals(table[ind].key)) {
                table[ind].value = v;
                return true;
            } else {
                boolean b = false;
                Listelement tmpls = table[ind].ls;
                while (b) {
                    if (tmpls.ls == null) {
                        tmpls.ls = new Listelement(k.hashCode(), k, v, null);
                        b = true;
                    } else {
                        tmpls = tmpls.ls;
                    }
                }
            }
        }
        return true;
    }

    public V getVal(K k) {
        int ind = size - 1 & k.hashCode();
        if (table[ind].ls == null) {
            return table[ind].value;
        } else {
            boolean b = false;
            Listelement<K, V> tmpls = table[ind].ls;
            while (b) {
                if (tmpls.ls == null) {
                    return tmpls.value;
                } else {
                    tmpls = tmpls.ls;
                }
            }
        }
        return null;
    }

    class Listelement<K, V> {
        private int hashCode;
        private K key;
        private V value;
        private Listelement ls;

        Listelement(int hashCode, K key, V value, Listelement ls) {
            this.hashCode = hashCode;
            this.key = key;
            this.value = value;
            this.ls = ls;
        }
    }
}

