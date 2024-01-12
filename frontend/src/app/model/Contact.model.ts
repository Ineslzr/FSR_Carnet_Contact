import { Address } from './Address.model';
import { PhoneNumber } from './phoneNumber.model';

export interface Contact {
  idContact?: number;
  firstName: string;
  lastName: string;
  email: string;
  addresse?: Address;
  phones?: PhoneNumber[];
}
