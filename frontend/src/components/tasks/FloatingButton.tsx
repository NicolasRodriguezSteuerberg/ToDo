import { Button, Checkbox, Flex, Icon, IconButton, Input, Modal, ModalBody, ModalContent, ModalHeader, ModalOverlay, useColorModeValue, useDisclosure } from "@chakra-ui/react";


const FloatingButton = () => {
    const { isOpen, onOpen, onClose } = useDisclosure();

    const onAddTask = () => {
        console.log("Adding new task")
        onClose();
    }

    return(<>
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
            onClick={onOpen}
        />

        <Modal isOpen={isOpen} onClose={onClose} isCentered>
            <ModalOverlay/>
            <ModalContent>
                <ModalHeader alignSelf={"center"}>Add new task</ModalHeader>
                <ModalBody>
                    <Flex direction="column">
                        <Input placeholder="Title"></Input>
                        <Checkbox>Terminada</Checkbox>
                        <Button onClick={onAddTask} width={"100%"}>+</Button>
                    </Flex>
                </ModalBody>
            </ModalContent>
        </Modal>
    </>)
};

export default FloatingButton;