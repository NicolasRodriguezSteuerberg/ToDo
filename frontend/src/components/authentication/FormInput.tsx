import { FormControl, FormLabel, Input } from "@chakra-ui/react";
import FormInputProps from "../../utils/props/FormInputPromp";


const FormInput: React.FC<FormInputProps> = ({
    id, label, type, placeholder, value, onChange
}) => {
    return(
        <FormControl id={id} isRequired>
            <FormLabel>{label}</FormLabel>
            <Input
                type={type}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
            />
        </FormControl>
    )
}

export default FormInput