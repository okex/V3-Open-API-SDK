package com.okcoin.commons.okex.open.api.bean.other;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

public class OrderBookDiffer {

    /*public <T extends OrderBookItem> List<T> diff(List<T> current, List<T> snapshot,
        final Comparator<BigDecimal> comparator, Function<BigDecimal, T> emptyItemFunction) {
        final Iterator<T> snapshotIter = snapshot.iterator();
        final Iterator<T> currentIter = current.iterator();
        final List<T> diff = new ArrayList<>(current.size());
        if (snapshotIter.hasNext() && currentIter.hasNext()) {
            T snapshotBookItem = snapshotIter.next();
            T currentBookItem = currentIter.next();
            for (;;) {
                final int compare = comparator.compare(snapshotBookItem.getPrice(), currentBookItem.getPrice());
                if (compare == 0) {
                    if (!snapshotBookItem.equals(currentBookItem)) {
                        diff.add(currentBookItem);
                    }
                    if (currentIter.hasNext() && snapshotIter.hasNext()) {
                        currentBookItem = currentIter.next();
                        snapshotBookItem = snapshotIter.next();
                    } else {
                        break;
                    }
                } else if (compare < 0) {
                    diff.add(emptyItemFunction.apply(snapshotBookItem.getPrice()));
                    if (snapshotIter.hasNext()) {
                        snapshotBookItem = snapshotIter.next();
                    } else {
                        diff.add(currentBookItem);
                        break;
                    }
                } else {
                    diff.add(currentBookItem);
                    if (currentIter.hasNext()) {
                        currentBookItem = currentIter.next();
                    } else {
                        diff.add(emptyItemFunction.apply(snapshotBookItem.getPrice()));
                        break;
                    }
                }
            }
        }

        if (!snapshotIter.hasNext()) {
            currentIter.forEachRemaining(diff::add);
        }
        if (!currentIter.hasNext()) {
            snapshotIter.forEachRemaining(remain -> diff.add(emptyItemFunction.apply(remain.getPrice())));
        }
        return Collections.unmodifiableList(diff);

    }*/

    //20200507 添加参数 order:  1正向排序  2反向排序
    public <T extends OrderBookItem> List<T> diff(List<T> current, List<T> snapshot,
                                                      final Comparator<String> comparator,int order) {

        //增量的数组
        final Iterator<T> snapshotIter = snapshot.iterator();
        //现有全量的数据
        final Iterator<T> currentIter = current.iterator();
        final List<T> diff = new ArrayList<>(current.size());
        if (snapshotIter.hasNext() && currentIter.hasNext()) {
            T snapshotBookItem = snapshotIter.next();
            T currentBookItem = currentIter.next();
            /*System.out.println("snapshotBookItem===================="+snapshotBookItem);
            System.out.println("currentBookItem======================="+currentBookItem);
            System.out.println("snapshotBookItem.getPrice()===================="+snapshotBookItem.getPrice());
            System.out.println("currentBookItem.getPrice()======================="+currentBookItem.getPrice());*/



            for (;;) {
//                final int compare = comparator.compare(snapshotBookItem.getPrice(), currentBookItem.getPrice());
                    double currentPrc = Double.parseDouble(currentBookItem.getPrice());
                    double snapPrc = Double.parseDouble(snapshotBookItem.getPrice());
                    int compare = 0;
                    if((order==1&&snapPrc > currentPrc)||(order==2&&snapPrc<currentPrc)) //增>全
                        compare = 1;
                    else if((order==1&&snapPrc < currentPrc)||(order==2&&snapPrc>currentPrc))//增<全
                        compare = -1;

                //价格相等时候
                if (compare == 0) {
                    /*if (!snapshotBookItem.equals(currentBookItem)) {
                        if(!"0".equals(snapshotBookItem.getSize().toString())){
                            diff.add(snapshotBookItem);
                        }
                    }*/

                    if(!"0".equals(snapshotBookItem.getSize().toString())){
                        diff.add(snapshotBookItem);
                    }


                    if (currentIter.hasNext() && snapshotIter.hasNext()) {
                        currentBookItem = currentIter.next();
                        snapshotBookItem = snapshotIter.next();
                    } else {
                        break;
                    }
                    //增量价格小于全量的
                } else if (compare < 0) {
                    if(!"0".equals(snapshotBookItem.getSize().toString())){
                        diff.add(snapshotBookItem);
                    }
                    if (snapshotIter.hasNext()) {
                        snapshotBookItem = snapshotIter.next();
                    } else {
                        diff.add(currentBookItem);
                        break;
                    }
                } else {
                    diff.add(currentBookItem);
                    if (currentIter.hasNext()) {
                        currentBookItem = currentIter.next();
                    } else {
                        if(!"0".equals(snapshotBookItem.getSize().toString())){
                            diff.add(snapshotBookItem);
                        }
                        break;
                    }
                }
            }
        }

        if (!snapshotIter.hasNext()) {
            currentIter.forEachRemaining(diff::add);
        }
        if (!currentIter.hasNext()) {
//            snapshotIter.forEachRemaining(remain -> diff.add(emptyItemFunction.apply(remain.getPrice())));
            snapshotIter.forEachRemaining(diff::add);
        }
        //返回不可修改的diff
        return Collections.unmodifiableList(diff);

    }
}
