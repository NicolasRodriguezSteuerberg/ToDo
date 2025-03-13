import { Button, Flex, Icon, IconButton, Input, Text, useColorModeValue, VStack } from "@chakra-ui/react"
import { useState } from "react"
import FormInput from "../components/authentication/FormInput"

const ToDoPage = () => {

    const [todoList, setTodoList] = useState<string[]>(['hola', 'adios']);
    const [input, setInput] = useState("");
    const appendTodo = (element: string) => {
        setTodoList((prev) => [...prev, element]);
    };

    return (
        <Flex justify={"center"} width={"100%"} p={5}>
            <IconButton
                icon={<Icon />}
                colorScheme="teal"
                aria-label="Add"
                size="lg"
                position="fixed"
                bottom="6"
                right="6"
                borderRadius="full"
                boxShadow="lg"
                bg={useColorModeValue('teal.500', 'teal.300')}
                color={useColorModeValue('white', 'gray.800')}
                _hover={{ bg: useColorModeValue('teal.600', 'teal.400') }}
                _active={{ bg: useColorModeValue('teal.700', 'teal.500') }}
                onClick={() => {console.log("AA")}}
                />

            <VStack spacing={4} shadow={"md"} width={'60%'}>
                {todoList.map((todo, index) => (
                    <Text key={index}>{todo}</Text>
                ))}
            </VStack>
        </Flex>
    )
}

export default ToDoPage