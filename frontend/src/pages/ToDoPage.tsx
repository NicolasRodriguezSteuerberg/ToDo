import { Flex, Text, VStack } from "@chakra-ui/react"
import { useState } from "react"
import FloatingButton from "../components/tasks/FloatingButton";

const ToDoPage = () => {

    const [todoList, setTodoList] = useState<string[]>(['hola', 'adios']);
    const [input, setInput] = useState("");
    const appendTodo = (element: string) => {
        setTodoList((prev) => [...prev, element]);
    };


    return (
        <Flex justify={"center"} width={"100%"} p={5}>
            <FloatingButton/>
            <VStack spacing={4} shadow={"md"} width={'60%'}>
                {todoList.map((todo, index) => (
                    <Text key={index}>{todo}</Text>
                ))}
            </VStack>
        </Flex>
    )
}

export default ToDoPage