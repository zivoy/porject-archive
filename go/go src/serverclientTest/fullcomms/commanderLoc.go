package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

var Commands = map[string]func([]string) string{
	"ping":   ping,
	"help":   help,
	"exit":   exit,
	"list":   getList,
	"append": appendList,
	"get":    getArg,
	"length": getLength,
}

var commandList []string

func initCommands() {
	commandList = make([]string, len(Commands))
	i := 0
	for k := range Commands {
		commandList[i] = k
		i++
	}
}

var list = make([]string, 0)

func CommandProcessor(input string) string {
	initCommands()

	if len(input) == 0 {
		return ""
	}

	parts := strings.Split(input, " ")

	if command, ok := Commands[strings.ToLower(parts[0])]; ok {
		return command(parts[1:])
	}
	return "Invalid command " + parts[0]
}

func ping(args []string) string {
	return fmt.Sprint("pong: ", args)
}

func help([]string) string {
	return strings.Join(commandList, ", ")
}

func getList([]string) string {
	return strings.Join(list, ", ")
}

func appendList(args []string) string {
	list = append(list, args...)
	return "DONE"
}

func getArg(args []string) string {
	if len(args) == 0 {
		return "index needed"
	}
	idx, err := strconv.Atoi(args[0])
	if err != nil {
		return fmt.Sprintf("`%s` is not a number", args[0])
	}
	if len(list) <= idx || 0 > idx {
		return fmt.Sprintf("%d is not in range", idx)
	}
	return list[idx]
}

func getLength([]string) string {
	return strconv.Itoa(len(list))
}

func exit([]string) string {
	os.Exit(0)
	return "DONE"
}
