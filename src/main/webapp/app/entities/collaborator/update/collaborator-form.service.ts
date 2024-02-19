import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICollaborator, NewCollaborator } from '../collaborator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICollaborator for edit and NewCollaboratorFormGroupInput for create.
 */
type CollaboratorFormGroupInput = ICollaborator | PartialWithRequiredKeyOf<NewCollaborator>;

type CollaboratorFormDefaults = Pick<NewCollaborator, 'id'>;

type CollaboratorFormGroupContent = {
  id: FormControl<ICollaborator['id'] | NewCollaborator['id']>;
  code: FormControl<ICollaborator['code']>;
  type: FormControl<ICollaborator['type']>;
  status: FormControl<ICollaborator['status']>;
  person: FormControl<ICollaborator['person']>;
};

export type CollaboratorFormGroup = FormGroup<CollaboratorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CollaboratorFormService {
  createCollaboratorFormGroup(collaborator: CollaboratorFormGroupInput = { id: null }): CollaboratorFormGroup {
    const collaboratorRawValue = {
      ...this.getFormDefaults(),
      ...collaborator,
    };
    return new FormGroup<CollaboratorFormGroupContent>({
      id: new FormControl(
        { value: collaboratorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(collaboratorRawValue.code, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(255)],
      }),
      type: new FormControl(collaboratorRawValue.type, {
        validators: [Validators.required],
      }),
      status: new FormControl(collaboratorRawValue.status, {
        validators: [Validators.required],
      }),
      person: new FormControl(collaboratorRawValue.person),
    });
  }

  getCollaborator(form: CollaboratorFormGroup): ICollaborator | NewCollaborator {
    return form.getRawValue() as ICollaborator | NewCollaborator;
  }

  resetForm(form: CollaboratorFormGroup, collaborator: CollaboratorFormGroupInput): void {
    const collaboratorRawValue = { ...this.getFormDefaults(), ...collaborator };
    form.reset(
      {
        ...collaboratorRawValue,
        id: { value: collaboratorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CollaboratorFormDefaults {
    return {
      id: null,
    };
  }
}
