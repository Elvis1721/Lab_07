package it.unibo.inner.api;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    
    private T[] array;
    private Predicate<T> filter;
    
    public IterableWithPolicyImpl(T[] elements){
        this(elements, new Predicate<T>() {
            
            @Override
            public boolean test(T elements) {
                return true;
            }
        });
    }

    public IterableWithPolicyImpl(T[] elements, Predicate<T> filt){
        this.array = elements;
        this.filter = filt;
    }
    

    public class InnIterator implements java.util.Iterator<T> {
            
        private int current = -1;

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            while (!filter.test(array[++this.current])){}
            return array[this.current];
        }

        @Override
        public boolean hasNext() {
            int i=1;
            while (this.current+i<array.length){
                if (filter.test(array[this.current+i])){
                    return true;
                }
                i++;
            }
            return false;
        }

        @Override
        public void remove() { 
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public java.util.Iterator<T> iterator (){
        return new InnIterator();
    }

    /**
     * Configures the policy used to filter out the elements to iterate.
     * @param filter the predicate used to filter out the elements in the collection during the iteration.
     */

    @Override 
    public void setIterationPolicy(Predicate<T> filter){
        this.filter = filter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        java.util.Iterator<T> it = this.iterator();
        
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}