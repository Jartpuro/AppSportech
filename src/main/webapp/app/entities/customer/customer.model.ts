import { IUser } from 'app/entities/user/user.model';
import { ITrainee } from 'app/entities/trainee/trainee.model';
import { ILogError } from 'app/entities/log-error/log-error.model';
import { ILogAudit } from 'app/entities/log-audit/log-audit.model';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { IDocumentType } from 'app/entities/document-type/document-type.model';

export interface ICustomer {
  id?: number;
  documentNumber?: string;
  firstName?: string;
  secondName?: string | null;
  fisrtLastName?: string;
  secondLastName?: string | null;
  user?: IUser;
  trainees?: ITrainee[] | null;
  logErrors?: ILogError[] | null;
  logAudits?: ILogAudit[] | null;
  trainers?: ITrainer[] | null;
  documentType?: IDocumentType;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public documentNumber?: string,
    public firstName?: string,
    public secondName?: string | null,
    public fisrtLastName?: string,
    public secondLastName?: string | null,
    public user?: IUser,
    public trainees?: ITrainee[] | null,
    public logErrors?: ILogError[] | null,
    public logAudits?: ILogAudit[] | null,
    public trainers?: ITrainer[] | null,
    public documentType?: IDocumentType
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
