package main

import (
	"fmt"
	"math/rand"
	"time"
)

var (
	maximum = History{maxLimit, time.Now()}
	minumum = History{minLimit, time.Now()}
)

const (
	maxLimit = 50
	minLimit = 0
	dumpTime = 5 * time.Second
)

type History struct {
	Value   int
	TimeSet time.Time
}

type HistoryQueue []History

func (h *HistoryQueue) append(history History) {
	*h = append(*h, history)
}

func (h *HistoryQueue) pop() History {
	v := (*h)[0]
	*h = (*h)[1:]
	return v
}

var maxQueue HistoryQueue
var minQueue HistoryQueue

func init() {
	maxQueue = make(HistoryQueue, 0)
	minQueue = make(HistoryQueue, 0)
	maxQueue.append(History{24, time.Now()})
	maxQueue.append(History{50, time.Now()})
	maxQueue.append(History{20, time.Now()})
}

func main() {
	c := time.After(60 * time.Second)
    rand.Seed(time.Now().Unix())

	for {
		select {
		case <-c:
			return
		default:
			r := rand.Intn(120)
			t := time.Now()
            h:= History{r, t}
            maxQueue.append(h)
            minQueue.append(h)

            // clear queue
            for i := 0; i<len(maxQueue);i++ {
                elm := maxQueue[i]
                if elm.Value <= r{
                    maxQueue = append(maxQueue[:i],maxQueue[i+1:]...)
                    i --
                }
            }
            for  i := 0; i<len(minQueue);i++{
                elm := minQueue[i]
                if elm.Value >= r  {//&& elm.TimeSet.Before(t)
                    minQueue = append(minQueue[:i],minQueue[i+1:]...)
                    i --
                }
            }

			// purge old things from queue
			for len(maxQueue) > 0 {
				if t.After(maxQueue[0].TimeSet.Add(dumpTime)) {
					maxQueue.pop()
				} else {
					break
				}
			}
			for len(minQueue) > 0 {
                if t.After(minQueue[0].TimeSet.Add(dumpTime)) {
					minQueue.pop()
				} else {
					break
				}
			}

			// revert MinMax
			if t.Sub(maximum.TimeSet) > dumpTime {
				if len(maxQueue) == 0 {
					//maxLimit
					maximum = History{r, time.Now()}
				} else {
					maximum = maxQueue.pop()
				}
			}
			if t.Sub(minumum.TimeSet) > dumpTime {
				if len(minQueue) == 0 {
					//minLimit
					minumum = History{r, time.Now()}
				} else {
					minumum = minQueue.pop()
				}
			}

            // update minMax
            if r > maximum.Value {
                maximum = h
            }
            if r < minumum.Value {
                minumum = h
            }

            m :=maprange(r, minumum.Value, maximum.Value, 0, 100)
            fmt.Printf("value: %-5dmapped: %-5d\tmin: %-5dmax: %d  \tqueue sizes: min: %-5dmax: %d\n", r,m, minumum.Value, maximum.Value, len(minQueue), len(maxQueue))
			time.Sleep(100 * time.Millisecond)
		}
	}
}

func maprange(val, pMin, pMax, aMin, aMax int) int {
	if pMax == pMin {
		return aMax
	}
	return ((val - pMin) * (aMax - aMin) / (pMax - pMin)) + aMin
}
