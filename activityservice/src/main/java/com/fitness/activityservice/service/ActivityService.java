package com.fitness.activityservice.service;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;
    private final UserValidationService userValidationService;

    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if (!isValidUser){
            throw new RuntimeException("Invalid user: "+ request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return modelMapper.map(savedActivity, ActivityResponse.class);

    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity>activities = activityRepository.findByUserId(userId);

        List<ActivityResponse>activityResponseList= activities.stream().map(activity -> modelMapper.map(activity, ActivityResponse.class)).toList();

        return activityResponseList;
    }

    public ActivityResponse getActivity(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(()->new RuntimeException("Activity not found with id: "+activityId));

        return modelMapper.map(activity, ActivityResponse.class);
    }
}
