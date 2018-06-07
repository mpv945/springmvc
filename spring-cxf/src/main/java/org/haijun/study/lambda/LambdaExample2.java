package org.haijun.study.lambda;

/**
 * Java 8 stream 分组 合并
 */
public class LambdaExample2 {

    public static void main(String[] args) {

/*        List<Buss> bussList = new ArrayList<>();
        bussList.add(new Buss("a",10,0.3));
        bussList.add(new Buss("b",3,0.8));
        bussList.add(new Buss("c",5,2.0));
        bussList.add(new Buss("b",30,3.2));
        bussList.add(new Buss("c",20,0.1));

        List<Buss> st = new ArrayList<>();
        bussList.stream()
                .collect(Collectors.groupingBy(Buss::getName)) //分组(Name can't be null)
                .forEach((k,v) -> {
                    Optional<Buss> sum = v.stream().reduce((v1,v2) -> {  //合并
                        v1.setCount(v1.getCount()+v2.getCount());
                        v1.setValue(v1.getValue()+v2.getValue());
                        return v1;
                    });
                    st.add(sum.orElse(new Buss("other",0,0.0)));

                });
        System.out.println(st);*/

        // 结果 [Buss{name='a', count=10, value=0.3}, Buss{name='b', count=33, value=4.0}, Buss{name='c', count=25, value=2.1}]


    }

    /*class Buss {

        private String name;
        private int count;
        private double value;

        public Buss(String name, int count, double value) {
            this.name = name;
            this.count = count;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Buss{" +
                    "name='" + name + '\'' +
                    ", count=" + count +
                    ", value=" + value +
                    '}';
        }
    }*/
}
