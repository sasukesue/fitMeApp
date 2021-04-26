import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'schedule',
        loadChildren: () => import('./schedule/schedule.module').then(m => m.AppScheduleModule)
      },
      {
        path: 'fitness-class',
        loadChildren: () => import('./fitnessClass/fitness-class/fitness-class.module').then(m => m.FitnessClassFitnessClassModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location/location.module').then(m => m.LocationLocationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AppEntityModule {}
