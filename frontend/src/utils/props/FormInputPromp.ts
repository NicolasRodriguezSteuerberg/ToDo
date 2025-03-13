export default interface FormInputProps {
    id: string;
    label: string;
    type: string;
    placeholder?: string;
    value: string;
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}